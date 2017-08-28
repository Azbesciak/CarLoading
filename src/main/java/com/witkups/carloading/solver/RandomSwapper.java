package com.witkups.carloading.solver;


import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collector;

import static java.util.stream.Collectors.*;

final class RandomSwapper {
    private final static Random randomGen = new Random();
    private static RandomSwapper instance;
    private final Map<Integer, List<PackagePlacement>> groupedBySection;

    static boolean swap(List<PackagePlacement> placements) {
        initializeIfNotExist(placements);
        return instance.swapInSection(placements);
    }

    private static void initializeIfNotExist(List<PackagePlacement> placements) {
        if (instance == null) {
            synchronized (SolutionsRepo.class) {
                if (instance == null) {
                    instance = new RandomSwapper(placements);
                }
            }
        }
    }

    private RandomSwapper(List<PackagePlacement> placements) {
        groupedBySection = getSequencesWithMultiplePackages(placements);
    }

    private Map<Integer, List<PackagePlacement>> getSequencesWithMultiplePackages(List<PackagePlacement> placements) {
        Map<Integer, List<PackagePlacement>> groupedBySection;
        groupedBySection = placements.stream()
                .collect(groupBySeqId());
        groupedBySection
                .entrySet().removeIf(entry -> entry.getValue().size() < 2);
        return groupedBySection;
    }

    private Collector<PackagePlacement, ?, Map<Integer, List<PackagePlacement>>> groupBySeqId() {
        return groupingBy(placement -> placement.getPack().getSequenceId());
    }

    private boolean swapInSection(List<PackagePlacement> currentPlacement) {
        if (!groupedBySection.isEmpty()) {
            final List<PackagePlacement> placements = getRandomSection();
            int first = getRandomPlacementInSection(currentPlacement, placements);
            int second = getRandomPlacementInSection(currentPlacement, placements);
            Collections.swap(currentPlacement, first, second);
            return true;
        }
        return false;
    }

    private int getRandomPlacementInSection(List<PackagePlacement> currentPlacement,
                                            List<PackagePlacement> placements) {
        final PackagePlacement firstPlacement = getAndRemoveRandomPlacement(placements);
        return getIndexInCurrentPlacement(firstPlacement, currentPlacement);
    }

    private PackagePlacement getAndRemoveRandomPlacement(List<PackagePlacement> placements) {
        final int firstPlacementIndex = randomGen.nextInt(placements.size());
        final PackagePlacement firstPlacement = placements.get(firstPlacementIndex);
        placements.remove(firstPlacementIndex);
        return firstPlacement;
    }

    private int getIndexInCurrentPlacement(PackagePlacement placement, List<PackagePlacement> currentPlacement) {
        final String currentPackId = placement.getPack().getId();
        for (int i = 0; i < currentPlacement.size(); i++) {
            final String verifiedPackId = currentPlacement.get(i).getPack().getId();
            if (StringUtils.equals(currentPackId, verifiedPackId)) {
                return i;
            }
        }
        throw new RuntimeException("Could not find package in declared section");
    }

    private List<PackagePlacement> getRandomSection() {
        final Object[] objects = groupedBySection.values().toArray();
        final int indexOfSectionPlacement = randomGen.nextInt(objects.length);
        return new ArrayList<>((List<PackagePlacement>) objects[indexOfSectionPlacement]);
    }
}
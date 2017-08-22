package com.witkups.carloading.solver;

import com.witkups.carloading.entity.*;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.validation.PackagePlacementValidator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solver {
    private final Instance instance;
    private final SolutionsRepo solutionsRepo;
    public Solver(Instance instance) {
        this.instance = instance;
        this.solutionsRepo = new SolutionsRepo();
    }

    Stream<Solution> prepareSolution() {
        final List<Package> packages = instance.getPackages();
        packages.sort(new InitialPackageOrderComparator());
        final Solution initialSolution = findInitialSolution(packages);
        solutionsRepo.add(initialSolution);
//        final Result result = new Result();
        return Stream.concat(
                Stream.of(initialSolution),
                Stream.generate(this::findSolution)
        ).parallel();//.filter(result::validate);
    }

    private Solution findSolution() {
        final Solution randomSolution = solutionsRepo.getRandom();
        tryToImproveGivenSolution(randomSolution);
        return solutionsRepo.getTheBest();
    }

    private void tryToImproveGivenSolution(Solution randomSolution) {
        final List<PackagePlacement> placementsFromSolution = randomSolution.getPackagePlacements();
        final List<PackagePlacement> changedPlacements = copyWithRandomChange(placementsFromSolution);
        placePackages(changedPlacements);
        final Solution solution = new Solution(changedPlacements);
        solutionsRepo.add(solution);
    }

    private Solution findInitialSolution(List<Package> packages) {
        final List<PackagePlacement> placements = initializePackagePlacements(packages);
        placePackages(placements);
        return new Solution(placements);
    }

    private List<PackagePlacement> initializePackagePlacements(List<Package> packages) {
        return packages.stream()
                .map(pack -> new PackagePlacement(pack, false, null))
                .collect(Collectors.toList());
    }

    private void placePackages(List<PackagePlacement> placements) {
        IntStream.range(0, placements.size())
                .forEach(packIndex -> placePackage(placements, packIndex));
    }

    private void placePackage(List<PackagePlacement> placements, int placementIndex) {
        final PackagePlacement packPlacement = placements.get(placementIndex);
        final boolean isPackageReversed = packPlacement.isPackageReversed();
        int widthLimit = instance.getVehicle().getWidth() - getPackageWidth(packPlacement.getPack(), isPackageReversed);
        for (int y = 0; ; y++)
            for (int x = 0; x <= widthLimit; x++) {
                final PackagePlacement placement = packPlacement.withPlacement(new Placement(x, y));
                placements.set(placementIndex, placement);
                final boolean isPlacedCorrect =
                        new PackagePlacementValidator(placement, instance.getVehicle())
                                .isPlacedCorrect(placements);
                if (isPlacedCorrect) {
                    return;
                }
            }
    }

    private int getPackageWidth(Package pack, boolean isPackageReversed) {
        return isPackageReversed ? pack.getHost().getLength() : pack.getHost().getWidth();
    }

    private List<PackagePlacement> copyWithRandomChange(List<PackagePlacement> placements) {
        final boolean shouldSwap = new Random().nextBoolean();
        final List<PackagePlacement> placementsCopy = copyPlacements(placements);
        if (shouldSwap) {
            makeRandomSwap(placementsCopy);
        } else {
            reverseRandomPlacement(placementsCopy);
        }
        return placementsCopy;
    }

    private ArrayList<PackagePlacement> copyPlacements(List<PackagePlacement> placements) {
        return new ArrayList<>(placements);
    }

    private void reverseRandomPlacement(List<PackagePlacement> placements) {
        final int randomPackagePlacementIndex = new Random().nextInt(placements.size());
        final PackagePlacement placement = placements.get(randomPackagePlacementIndex);
        final boolean changed = !placement.isPackageReversed();
        placements.set(randomPackagePlacementIndex, placement.withPackageReversed(changed));
    }

    private void makeRandomSwap(List<PackagePlacement> placements) {
        for (int i = 0; i < placements.size() - 1; i++) {
            for (int y = 0; y < placements.size(); y++) {
                if (swapIfPossible(placements, i, y)) {
                    return;
                }
            }
        }
    }

    private boolean swapIfPossible(List<PackagePlacement> placements, int p1, int p2) {
        if (placements.get(p1).getPack().getSequenceId() ==
                placements.get(p2).getPack().getSequenceId()) {
            Collections.swap(placements, p1, p2);
            return true;
        }
        return false;
    }


    private class Result {
        Solution solution;

        synchronized boolean validate(Solution solution) {
            if (solution.isBetterThan(this.solution)) {
                this.solution = solution;
                return true;
            }
            return false;
        }
    }
}

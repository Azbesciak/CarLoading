package com.witkups.carloading.solver;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;

import java.util.*;

final class ContinuousSolutionMaker extends SolutionMaker {
    private static final Random rand = new Random();

    ContinuousSolutionMaker(Instance instance) {
        super(instance);
    }

    @Override
    public Solution findSolution() {
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

    private List<PackagePlacement> copyWithRandomChange(List<PackagePlacement> placements) {
        final boolean shouldSwap = rand.nextBoolean();
        final List<PackagePlacement> placementsCopy = copyPlacements(placements);

        if (shouldSwap) {
            final boolean isSwapped = RandomSwapper.swap(placementsCopy);
            if (isSwapped) {
                return placementsCopy;
            }
        }

        reverseRandomPlacement(placementsCopy);
        return placementsCopy;
    }

    private ArrayList<PackagePlacement> copyPlacements(List<PackagePlacement> placements) {
        return new ArrayList<>(placements);
    }

    private void reverseRandomPlacement(List<PackagePlacement> placements) {
        final int randomPackagePlacementIndex = rand.nextInt(placements.size());
        final PackagePlacement placement = placements.get(randomPackagePlacementIndex);
        final boolean changed = !placement.isPackageReversed();
        placements.set(randomPackagePlacementIndex, placement.withPackageReversed(changed));
    }
}
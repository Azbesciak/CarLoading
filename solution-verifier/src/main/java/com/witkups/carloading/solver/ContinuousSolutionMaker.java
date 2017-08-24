package com.witkups.carloading.solver;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class ContinuousSolutionMaker extends SolutionMaker {
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
		final boolean shouldSwap = new Random().nextBoolean();
		final List<PackagePlacement> placementsCopy = copyPlacements(placements);

		if (shouldSwap) {
			final boolean isSwapped = makeRandomSwap(placementsCopy);
			if (isSwapped)
				return placementsCopy;
		}

		reverseRandomPlacement(placementsCopy);
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

	private boolean makeRandomSwap(List<PackagePlacement> placements) {
		for (int i = 0; i < placements.size() - 1; i++) {
			for (int y = 0; y < placements.size(); y++) {
				if (swapIfPossible(placements, i, y)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean swapIfPossible(List<PackagePlacement> placements, int p1, int p2) {
		if (placements.get(p1)
		              .getPack()
		              .getSequenceId() == placements.get(p2)
		                                            .getPack()
		                                            .getSequenceId()) {
			Collections.swap(placements, p1, p2);
			return true;
		}
		return false;
	}
}
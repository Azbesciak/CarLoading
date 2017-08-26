package com.witkups.carloading.solver;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.Placement;
import com.witkups.carloading.validation.packageplacements.PackagePlacementValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

abstract class SolutionMaker {
	protected final Instance instance;
	protected final SolutionsRepo solutionsRepo;

	SolutionMaker(Instance instance) {
		this.instance = instance;
		this.solutionsRepo = SolutionsRepo.getRepoInstance();
	}

	abstract Solution findSolution();

	protected void placePackages(List<PackagePlacement> placements) {
		IntStream.range(0, placements.size())
		         .forEach(packIndex -> placePackage(placements, packIndex));
	}

	protected void placePackage(List<PackagePlacement> placements, int placementIndex) {
		final PackagePlacement packPlacement = placements.get(placementIndex);
		final boolean isPackageReversed = packPlacement.isPackageReversed();
		int widthLimit = instance.getVehicle()
		                         .getWidth() - getPackageWidth(packPlacement.getPack(), isPackageReversed);
		for (int y = 0; ; y++)
			for (int x = 0; x <= widthLimit; ) {
				final PackagePlacement placement = updatePackPlacement(placements, placementIndex, packPlacement, y, x);
				final PackagePlacementValidator validator = getValidator(placement);
				final boolean isPlacedCorrect = validator.isPlacedCorrect(placements);
				if (isPlacedCorrect) {
					return;
				}
				else {
					x = moveAwayFromTheObstacle(x, validator);
				}
			}
	}

	private int moveAwayFromTheObstacle(int x, PackagePlacementValidator validator) {
		return validator.getObstacle()
		                .map(p -> p.asViewFromAbove().getMaxX())
		                .orElse(x + 1.0).intValue();
	}

	private PackagePlacement updatePackPlacement(List<PackagePlacement> placements, int placementIndex,
			PackagePlacement packPlacement, int y, int x) {
		final PackagePlacement placement = packPlacement.withPlacement(new Placement(x, y));
		placements.set(placementIndex, placement);
		return placement;
	}

	private PackagePlacementValidator getValidator(PackagePlacement placement) {
		return new PackagePlacementValidator(placement, instance.getVehicle());
	}

	private int getPackageWidth(Package pack, boolean isPackageReversed) {
		return isPackageReversed ?
				pack.getHost()
				    .getLength() :
				pack.getHost()
				    .getWidth();
	}
}
package com.witkups.carloading.validation.packageplacements;

import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PackagePlacementValidator {
	private final PackagePlacement packagePlacement;
	private final Rectangle placementFromAbove;
	private final Vehicle vehicle;
	private boolean throwErrorOnFail = false;
	@Getter
	private Optional<PackagePlacement> obstacle = Optional.empty();

	private PackagePlacementValidator(PackagePlacement packagePlacement, Vehicle vehicle, boolean throwErrorOnFail) {
		this(packagePlacement, vehicle);
		this.throwErrorOnFail = throwErrorOnFail;
	}

	public PackagePlacementValidator(PackagePlacement packagePlacement, Vehicle vehicle) {
		this.packagePlacement = packagePlacement;
		this.vehicle = vehicle;
		this.placementFromAbove = packagePlacement.asViewFromAbove();
	}

	public static boolean checkPlacement(
			List<PackagePlacement> packagePlacements,
			Vehicle vehicle,
			boolean throwErrorOnFail) {
		return packagePlacements.stream()
		                        .allMatch(placement ->
				                                  new PackagePlacementValidator(placement, vehicle, throwErrorOnFail)
						                                  .isPlacedCorrect(packagePlacements)
		                                 );
	}

	public boolean isPlacedCorrect(List<PackagePlacement> allPackagePlacements) {
		final int validatedPackIndex = allPackagePlacements.indexOf(packagePlacement);
		if (validatedPackIndex == -1) {
			throw new PlacementValidationError("Package is not placed", packagePlacement);
		} else {
			return canBePlaced(allPackagePlacements.subList(0, validatedPackIndex));
		}
	}

	public boolean canBePlaced(List<PackagePlacement> precedingPackagePlacements) {
		if (!precedingPackagePlacements.contains(packagePlacement)) {
			if (isWidthConditionFulfilled()
					&& allPrecedingPackagesHaveNotGreaterSequenceId(precedingPackagePlacements)) {

				final List<PackagePlacement> packagesBelow = getPackagesBellow(precedingPackagePlacements);
				return canBePlacedInThatPlace(packagesBelow) &&
				       isStackNotHigherThanVehicle(packagesBelow) &&
				       noPackageWhichDisturbAccess(precedingPackagePlacements);
			}
			return false;
		} else {
			throw new PlacementValidationError("Package already placed!", packagePlacement);
		}
	}

	private boolean allPrecedingPackagesHaveNotGreaterSequenceId(List<PackagePlacement> precedingPackagePlacements) {
		final boolean result = precedingPackagePlacements.stream()
		                                                 .allMatch(this::hasNotGreaterSequenceId);
		throwErrorIfShould(result, "package with greater sequence id is already placed");
		return result;
	}

	private boolean canBePlacedInThatPlace(List<PackagePlacement> packagesBelow) {
		final boolean result = packagesBelow.stream()
		                                    .allMatch(this::canBePlacedOnPackage);
		throwErrorIfShould(result, "packages stack is higher than vehicle");
		return result;
	}

	private boolean isStackNotHigherThanVehicle(List<PackagePlacement> packagesBelow) {
		final int stackHeight =
				packagesBelow.stream()
				             .map(packPlacement -> packPlacement.getPack().getHeight())
				             .reduce(0, Integer::sum) + packagePlacement.getPack().getHeight();
		final boolean result = stackHeight <= vehicle.getHeight();
		throwErrorIfShould(result, "packages stack is higher than vehicle");
		return result;
	}

	private List<PackagePlacement> getPackagesBellow(List<PackagePlacement> precedingPackagePlacements) {
		return precedingPackagePlacements.stream()
		                                 .filter(this::isInOtherPackageField)
		                                 .collect(Collectors.toList());
	}

	private boolean isWidthConditionFulfilled() {
		final boolean result =
				vehicle.getWidth() >= placementFromAbove.getMaxX() &&
				placementFromAbove.getX() >= 0;
		throwErrorIfShould(result, "package is out of vehicle width");
		return result;
	}

	private boolean isInOtherPackageField(PackagePlacement otherPackagePlacement) {
		return placementFromAbove.intersects(otherPackagePlacement.asViewFromAbove());
	}

	private boolean canBePlacedOnPackage(PackagePlacement otherPackPlacement) {
		final boolean canBePlacedOn =
				packagesHaveTheSamePlacementCords(otherPackPlacement) &&
				packBellowTotallyContainThatPack(otherPackPlacement) &&
				otherPackPlacement.getPack().canOtherPackageBePlacedOn() &&
				packagePlacement.getPack().canBePlacedOnPackage() &&
				hasNotGreaterSequenceId(otherPackPlacement);
		rememberAsObstacleIfCannotBePlacedOn(otherPackPlacement, canBePlacedOn);
		throwErrorIfShould(canBePlacedOn,
		                   "package cannot be placed on package " + otherPackPlacement.getPack().getId());
		return canBePlacedOn;
	}

	private void rememberAsObstacleIfCannotBePlacedOn(PackagePlacement otherPackPlacement, boolean canBePlacedOn) {
		if (!canBePlacedOn) {
			obstacle = Optional.of(otherPackPlacement);
		}
	}

	private boolean packBellowTotallyContainThatPack(PackagePlacement otherPackPlacement) {
		return otherPackPlacement.asViewFromAbove().contains(placementFromAbove);
	}

	private boolean packagesHaveTheSamePlacementCords(PackagePlacement otherPackPlacement) {
		return otherPackPlacement.getPlacement().equals(packagePlacement.getPlacement());
	}

	private boolean hasNotGreaterSequenceId(PackagePlacement otherPackPlacement) {
		return otherPackPlacement.getPack().getSequenceId() <= packagePlacement.getPack().getSequenceId();
	}

	private boolean noPackageWhichDisturbAccess(List<PackagePlacement> precedingPackagePlacements) {
		final boolean result = precedingPackagePlacements.stream()
		                                                 .filter(this::onlyFromPrecedingSequences)
		                                                 .map(PackagePlacement::asViewFromAbove)
		                                                 .filter(this::justCloserToTheExit)
		                                                 .allMatch(this::canAccessValidatedPackage);
		throwErrorIfShould(result, "some package disturbs access");
		return result;
	}

	private boolean onlyFromPrecedingSequences(PackagePlacement otherPackagePlacement) {
		return otherPackagePlacement.getPack().getSequenceId() < packagePlacement.getPack().getSequenceId();
	}

	private boolean justCloserToTheExit(Rectangle otherViewFromAbove) {
		return otherViewFromAbove.getMaxY() > placementFromAbove.getMaxY();
	}

	private boolean canAccessValidatedPackage(Rectangle otherViewFromAbove) {
		return otherViewFromAbove.getMaxX() <= placementFromAbove.getX() ||
				otherViewFromAbove.getX() >= placementFromAbove.getMaxX();
	}

	private void throwErrorIfShould(boolean result, String reason) {
		if (!result && throwErrorOnFail) {
			throwErr(reason);
		}
	}

	private void throwErr(String reason) {
		throw new PlacementValidationError(reason, packagePlacement);
	}
}
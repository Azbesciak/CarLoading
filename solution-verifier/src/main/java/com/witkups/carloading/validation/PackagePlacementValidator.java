package com.witkups.carloading.validation;

import com.witkups.carloading.entity.PackagePlacement;
import com.witkups.carloading.entity.Vehicle;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PackagePlacementValidator {

    public static boolean throwErrorIfFail = false;

    private PackagePlacement packagePlacement;
    private Rectangle placementFromAbove;
    private Vehicle vehicle;

    public PackagePlacementValidator(PackagePlacement packagePlacement, Vehicle vehicle) {

        this.packagePlacement = packagePlacement;
        this.vehicle = vehicle;
        this.placementFromAbove = packagePlacement.asViewFromAbove();
    }

    public static boolean checkPlacement(List<PackagePlacement> packagePlacements, Vehicle vehicle) {
        return packagePlacements.stream().allMatch(placement ->
                new PackagePlacementValidator(placement, vehicle).isPlacedCorrect(packagePlacements)
        );
    }

    public boolean isPlacedCorrect(List<PackagePlacement> allPackagePlacements) {
        final int validatedPackIndex = allPackagePlacements.indexOf(packagePlacement);
        if (validatedPackIndex == -1) {
            throw new IllegalStateException("Package is not placed");
        } else {
            return canBePlaced(allPackagePlacements.subList(0, validatedPackIndex));
        }
    }

    public boolean canBePlaced(List<PackagePlacement> precedingPackagePlacements) {
        if (!precedingPackagePlacements.contains(packagePlacement)) {
            if (isWidthConditionFulfilled()) {
                final List<PackagePlacement> packagesBelow = getPackagesBellow(precedingPackagePlacements);
                return canBePlacedInThatPlace(packagesBelow) &&
                        isStackNotHigherThanVehicle(packagesBelow) &&
                        noPackageWhichDisturbAccess(precedingPackagePlacements);
            }
            return false;
        } else {
            throw new IllegalStateException("Package already placed!");
        }
    }

    private boolean canBePlacedInThatPlace(List<PackagePlacement> packagesBelow) {
        final boolean result = packagesBelow.stream().allMatch(this::canBePlacedOnPackage);
        if (!result && throwErrorIfFail) {
            throw new PlacementValidationError("packages stack is higher than vehicle", packagePlacement);
        }
        return result;
    }

    private boolean isStackNotHigherThanVehicle(List<PackagePlacement> packagesBelow) {
        final int stackHeight = packagesBelow.stream()
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
        final boolean result = vehicle.getWidth() >= placementFromAbove.getMaxX() && placementFromAbove.getX() >= 0;
        throwErrorIfShould(result, "package is out of vehicle width");
        return result;
    }

    private boolean isInOtherPackageField(PackagePlacement otherPackagePlacement) {
        return placementFromAbove.intersects(otherPackagePlacement.asViewFromAbove());
    }

    private boolean canBePlacedOnPackage(PackagePlacement otherPackPlacement) {
        final boolean result = otherPackPlacement.asViewFromAbove().contains(placementFromAbove) &&
                otherPackPlacement.getPack().isCanOtherPackageBePlacedOn() &&
                packagePlacement.getPack().isCanBePlacedOnPackage() &&
                otherPackPlacement.getPack().getSequenceId() <= packagePlacement.getPack().getSequenceId();
        throwErrorIfShould(result, "package cannot be placed on package " + otherPackPlacement.getPack().getId());
        return result;
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
        if (!result && throwErrorIfFail) {
            throwErr(reason);
        }
    }

    private void throwErr(String reason) {
        throw new PlacementValidationError(reason, packagePlacement);
    }
}

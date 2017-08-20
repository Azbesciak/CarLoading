package com.witkups.carloading;

import com.witkups.carloading.entity.Host;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.entity.Purpose;
import com.witkups.carloading.entity.Vehicle;
import com.witkups.carloading.parser.input.Instance;
import com.witkups.carloading.parser.output.Solution;
import com.witkups.carloading.validation.Constraints;
import com.witkups.carloading.validation.PackagePlacementValidator;
import com.witkups.carloading.validation.PlacementValidationError;

import java.util.ArrayList;
import java.util.List;

public class InputDataValidator {

    public static void validate(Instance instance, Solution solution, Constraints constraints) {
        List<String> errors = new ArrayList<>();
        final Vehicle vehicle = instance.getVehicle();
        errors.addAll(validateVehicle(constraints, vehicle));
        final List<Package> packages = instance.getPackages();
        errors.addAll(validatePackages(constraints, packages));
        final List<Host> hosts = instance.getHosts();
        errors.addAll(validateHosts(constraints, hosts));
        errors.addAll(validateSolution(solution, instance));
        errors.forEach(System.err::println);

    }

    private static List<String> validateSolution(Solution solution, Instance instance) {
        List<String> errors = new ArrayList<>();
        final Purpose inputPurpose = solution.getPurpose();
        if (inputPurpose.getMaxDistance() < 0) {
            errors.add("Solution max distance is negative");
        }
        final Purpose validPurpose = new Solution(solution.getPackagePlacements()).getPurpose();
        if (validPurpose.getMaxDistance() != inputPurpose.getMaxDistance()) {
            errors.add("Max distance is not correct - should be " + validPurpose.getMaxDistance());
        }
        if (validPurpose.getOccupiedPlace() != inputPurpose.getOccupiedPlace()) {
            errors.add("Occupied place should equal to " + validPurpose.getOccupiedPlace());
        }

        PackagePlacementValidator.throwErrorIfFail = true;
        try {
            PackagePlacementValidator.checkPlacement(solution.getPackagePlacements(), instance.getVehicle());
        } catch (PlacementValidationError e) {
            errors.add(e.getMessage() + "(caused by pack " + e.getPlacement().getPack().getId() + ")");
        }

        return errors;
    }

    private static List<String> validateHosts(Constraints constraints, List<Host> hosts) {
        List<String> errors = new ArrayList<>();
        if (hosts.size() > constraints.getMaxHosts()) {
            errors.add(formatError("More hosts than allowed", constraints.getMaxHosts()));
        }
        final Integer maxLength = hosts.stream().map(Host::getLength).max(Integer::compare).orElse(0);
        if (maxLength > constraints.getMaxHostLength()) {
            errors.add(formatError("Host length is bigger than allowed", constraints.getMaxHostLength()));
        }
        final Integer maxWidth = hosts.stream().map(Host::getWidth).max(Integer::compare).orElse(0);
        if (maxWidth > constraints.getMaxHostWidth()) {
            errors.add(formatError("Host width is bigger than allowed", constraints.getMaxHostWidth()));
        }
        return errors;
    }

    private static List<String> validatePackages(Constraints constraints, List<Package> packages) {
        final List<String> errors = new ArrayList<>();
        final long sequences = packages.stream()
                .map(Package::getSequenceId)
                .distinct()
                .count();
        if (sequences > constraints.getMaxSequences()) {
            errors.add(formatError("More sequences than allowed", constraints.getMaxSequences()));
        }
        if (packages.size() > constraints.getMaxPackages()) {
            errors.add(formatError("More packages than allowed", constraints.getMaxPackages()));
        }
        final Integer maxHeight = packages.stream()
                .map(Package::getHeight)
                .max(Integer::compare)
                .orElse(0);
        if (maxHeight > constraints.getMaxPackageHeight()) {
            errors.add(formatError("Package is higher than allowed", constraints.getMaxPackageHeight()));
        }

        return errors;
    }

    private static List<String> validateVehicle(Constraints constraints, Vehicle vehicle) {
        List<String> errors = new ArrayList<>();
        if (vehicle.getHeight() > constraints.getMaxVehicleHeight()) {
            errors.add(formatError("Vehicle too high", constraints.getMaxVehicleHeight()));
        }
        if (vehicle.getWidth() <= 0) {
            errors.add("Vehicle width should be positive");
        }
        if (vehicle.getWidth() > constraints.getMaxVehicleWidth()) {
            errors.add(formatError("Vehicle too wide", constraints.getMaxVehicleWidth()));
        }
        if (vehicle.getHeight() <= 0) {
            errors.add("Vehicle height should be positive");
        }
        return errors;
    }

    private static String formatError(String error, int constraint) {
        return String.format("%s; constraint - %d", error, constraint);
    }
}

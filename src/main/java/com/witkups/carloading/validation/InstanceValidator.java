package com.witkups.carloading.validation;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.validation.constraints.Constraints;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class InstanceValidator {
	private final Instance instance;
	private final Constraints constraints;

	public void validate() {
		validateVehicle();
		validateHosts();
		validatePackages();
	}

	private void validateHosts() {
		final List<Host> hosts = instance.getHosts();
		final long distinctIdCount = hosts.stream().map(Host::getId).distinct().count();
		if (distinctIdCount != hosts.size()) {
			throw new ConstraintsValidationError("Host ids must be unique");
		}

		if (hosts.size() > constraints.getMaxHosts()) {
			throw new ConstraintsValidationError("More hosts than allowed", constraints.getMaxHosts());
		}
		final Integer maxLength = hosts.stream()
		                               .map(Host::getLength)
		                               .max(Integer::compare)
		                               .orElse(0);
		if (maxLength > constraints.getMaxHostLength()) {
			throw new ConstraintsValidationError("Host length is bigger than allowed", constraints.getMaxHostLength());
		}
		final Integer maxWidth = hosts.stream()
		                              .map(Host::getWidth)
		                              .max(Integer::compare)
		                              .orElse(0);
		if (maxWidth > constraints.getMaxHostWidth()) {
			throw new ConstraintsValidationError("Host width is bigger than allowed", constraints.getMaxHostWidth());
		}
	}

	private void validatePackages() {
		final List<Package> packages = instance.getPackages();

		final long distinctIdCount = packages.stream().map(Package::getId).distinct().count();
		if (distinctIdCount != packages.size()) {
			throw new ConstraintsValidationError("Package ids must be unique");
		}

		final long sequences = packages.stream()
		                               .map(Package::getSequenceId)
		                               .distinct()
		                               .count();
		if (sequences > constraints.getMaxSequences()) {
			throw new ConstraintsValidationError("More sequences than allowed", constraints.getMaxSequences());
		}
		if (packages.size() > constraints.getMaxPackages()) {
			throw new ConstraintsValidationError("More packages than allowed", constraints.getMaxPackages());
		}
		final Integer maxHeight = packages.stream()
		                                  .map(Package::getHeight)
		                                  .max(Integer::compare)
		                                  .orElse(0);
		if (maxHeight > constraints.getMaxPackageHeight()) {
			throw new ConstraintsValidationError("Package is higher than allowed", constraints.getMaxPackageHeight());
		}
	}

	private void validateVehicle() {
		final Vehicle vehicle = instance.getVehicle();
		if (vehicle.getHeight() > constraints.getMaxVehicleHeight()) {
			throw new ConstraintsValidationError("Vehicle too high", constraints.getMaxVehicleHeight());
		}
		if (vehicle.getWidth() <= 0) {
			throw new ConstraintsValidationError("Vehicle width should be positive");
		}
		if (vehicle.getWidth() > constraints.getMaxVehicleWidth()) {
			throw new ConstraintsValidationError("Vehicle too wide", constraints.getMaxVehicleWidth());
		}
		if (vehicle.getHeight() <= 0) {
			throw new ConstraintsValidationError("Vehicle height should be positive");
		}
	}
}

package com.witkups.carloading.validation;

import com.witkups.carloading.instance.Instance;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.purpose.Purpose;
import lombok.AllArgsConstructor;

import static com.witkups.carloading.validation.packageplacements.PackagePlacementValidator.checkPlacement;

@AllArgsConstructor
public final class SolutionValidator {
	private final Instance instance;
	private final Solution solution;

	public void validate() {
		validatePurpose();
		checkPlacement(solution.getPackagePlacements(), instance.getVehicle(), true);
	}

	private void validatePurpose() {
		final Purpose inputPurpose = solution.getPurpose();
		if (inputPurpose.getMaxDistance() < 0) {
			throw new ConstraintsValidationError("Solution max distance is negative");
		}
		final Purpose validPurpose = new Solution(solution.getPackagePlacements()).getPurpose();
		if (validPurpose.getMaxDistance() != inputPurpose.getMaxDistance()) {
			throw new ConstraintsValidationError(
					"Max distance is not correct - should be " + validPurpose.getMaxDistance());
		}
		if (validPurpose.getOccupiedPlace() != inputPurpose.getOccupiedPlace()) {
			throw new ConstraintsValidationError("Occupied place should equal to " + validPurpose.getOccupiedPlace());
		}
	}
}
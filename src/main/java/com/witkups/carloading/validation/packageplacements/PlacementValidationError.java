package com.witkups.carloading.validation.packageplacements;

import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.validation.ValidationError;
import lombok.Getter;

public class PlacementValidationError extends ValidationError {
	@Getter
	private final PackagePlacement placement;

	PlacementValidationError(String reason, PackagePlacement placement) {
		super(reason);
		this.placement = placement;
	}

}

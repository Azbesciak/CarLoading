package com.witkups.carloading.validation.packageplacements;

import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.validation.ValidationError;
import lombok.Getter;

public final class PlacementValidationError extends ValidationError {
	private final PackagePlacement placement;

	PlacementValidationError(String reason, PackagePlacement placement) {
		super(reason);
		this.placement = placement;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + "(caused by pack " + placement.getPack().getId() + ")";
	}
}

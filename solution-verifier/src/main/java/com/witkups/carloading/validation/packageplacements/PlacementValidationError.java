package com.witkups.carloading.validation.packageplacements;

import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import lombok.Getter;


public class PlacementValidationError extends RuntimeException {
    @Getter
    private final PackagePlacement placement;

    public PlacementValidationError(String reason, PackagePlacement placement) {
        super(reason);
        this.placement = placement;
    }


}

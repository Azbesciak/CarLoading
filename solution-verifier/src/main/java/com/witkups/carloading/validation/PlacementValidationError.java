package com.witkups.carloading.validation;

import com.witkups.carloading.entity.PackagePlacement;
import lombok.Getter;


public class PlacementValidationError extends RuntimeException {
    @Getter
    private final PackagePlacement placement;

    public PlacementValidationError(String reason, PackagePlacement placement) {
        super(reason);
        this.placement = placement;
    }


}

package com.witkups.carloading.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@AllArgsConstructor
@Builder
public class PackagePlacement {
    private final Package pack;
    private final boolean isReversed;
    private final int distanceToLeftEdge;
    private final int distanceToVehicleFront;
}

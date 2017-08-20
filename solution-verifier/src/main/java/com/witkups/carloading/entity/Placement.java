package com.witkups.carloading.entity;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Placement {
    private final int distanceToLeftEdge;
    private final int distanceToVehicleFront;
}

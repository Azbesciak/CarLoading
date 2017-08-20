package com.witkups.carloading.entity;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@AllArgsConstructor
@Wither
public class Placement {
    private final int distanceToLeftEdge;
    private final int distanceToVehicleFront;
}

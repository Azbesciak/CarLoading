package com.witkups.carloading.solution.packageplacements;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@AllArgsConstructor
@Wither
public final class Placement {
	private final int distanceToLeftEdge;
	private final int distanceToVehicleFront;
}

package com.witkups.carloading.validation.constraints;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Constraints {
	private final int maxVehicleWidth;
	private final int maxVehicleHeight;
	private final int maxSequences;
	private final int maxPackages;
	private final int maxHosts;
	private final int maxHostLength;
	private final int maxHostWidth;
	private final int maxPackageHeight;
}

package com.witkups.carloading.solution.packageplacements;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class PackagePlacementsSectionStructure {
	static final int PACKAGE_ID_INDEX = 0;
	static final int IS_REVERSED_INDEX = 1;
	static final int DISTANCE_TO_LEFT_EDGE_INDEX = 2;
	static final int DISTANCE_TO_VEHICLE_FRONT_INDEX = 3;
	static final int PLACEMENT_FIELDS = 4;
}
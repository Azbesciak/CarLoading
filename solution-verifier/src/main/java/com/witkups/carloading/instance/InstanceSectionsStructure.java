package com.witkups.carloading.instance;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class InstanceSectionsStructure {
	static final int VEHICLE_SECTION_INDEX = 0;
	static final int HOSTS_SECTION_INDEX = 1;
	static final int PACKAGES_SECTION_INDEX = 2;
	static final int INSTANCE_SECTIONS = 3;
}

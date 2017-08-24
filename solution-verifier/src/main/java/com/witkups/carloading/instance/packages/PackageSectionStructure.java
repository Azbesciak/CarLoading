package com.witkups.carloading.instance.packages;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class PackageSectionStructure {
	static final int ID_INDEX = 0;
	static final int SEQUENCE_ID_INDEX = 1;
	static final int HOST_ID_INDEX = 2;
	static final int HEIGHT_INDEX = 3;
	static final int CAN_BE_PLACED_ON_PACKAGE_INDEX = 4;
	static final int CAN_OTHER_PACKAGE_BE_PLACED_ON_INDEX = 5;
	static final int PACKAGE_FIELDS = 6;
}

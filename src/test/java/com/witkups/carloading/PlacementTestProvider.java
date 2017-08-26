package com.witkups.carloading;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.Placement;

import java.util.Random;

public class PlacementTestProvider {
	private static Random random = new Random();
	public static PackagePlacement prepareMediumPackagePlacement(int length, int height, int width, int sequenceId,
			int x,
			int y, boolean canBePlacedOn, boolean canOtherBePlacedOn) {
		return PackagePlacement.builder()
		                       .pack(Package.builder()
		                                    .id(String.valueOf(random.nextInt()))
		                                    .height(height)
		                                    .host(Host.builder()
		                                              .width(width)
		                                              .length(length)
		                                              .build())
		                                    .sequenceId(sequenceId)
		                                    .canBePlacedOnPackage(canBePlacedOn)
		                                    .canOtherPackageBePlacedOn(canOtherBePlacedOn)
		                                    .build())
		                       .placement(new Placement(x, y))
		                       .build();
	}

	public static PackagePlacement prepareSimplePlacement(int length, int width, int x, int y) {
		return new PackagePlacement(Package.builder()
		                                   .id(String.valueOf(random.nextInt()))
		                                   .host(Host.builder()
		                                             .length(length)
		                                             .width(width)
		                                             .build())
		                                   .build(), false, new Placement(x, y));
	}
}
package com.witkups.carloading.solution;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.solution.Solution;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.Placement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SolutionTest {
	@Test
	public void maxDistanceTest() {
		final PackagePlacement p1 = preparePlacement(5, 10, 10, 10);
		final PackagePlacement p2 = preparePlacement(3, 10, 0, 10);
		final PackagePlacement p3 = preparePlacement(10, 10, 10, 10);
		final List<PackagePlacement> packagePlacements = new ArrayList<>(Arrays.asList(p1, p2, p3));
		final Solution solution = new Solution(packagePlacements);
		assertEquals(20, solution.getPurpose()
		                         .getMaxDistance());
		final PackagePlacement p4 = preparePlacement(5, 2, 40, 19);
		packagePlacements.add(p4);
		final Solution solution1 = new Solution(packagePlacements);
		assertEquals(21, solution1.getPurpose()
		                          .getMaxDistance());
		final PackagePlacement p5 = preparePlacement(5, 5, 40, 22);
		packagePlacements.add(p5);
		final Solution solution2 = new Solution(packagePlacements);
		assertEquals(27, solution2.getPurpose()
		                          .getMaxDistance());

		final PackagePlacement p6 = preparePlacement(5, 5, 13, 10);
		packagePlacements.add(p6);
		final Solution solution3 = new Solution(packagePlacements);
		assertEquals(27, solution3.getPurpose()
		                          .getMaxDistance());

	}

	@Test
	public void occupiedFieldTest() {
		final PackagePlacement p1 = preparePlacement(5, 10, 10, 10);
		final ArrayList<PackagePlacement> placements = new ArrayList<>(Collections.singletonList(p1));
		assertEquals(50, new Solution(placements).getPurpose()
		                                         .getOccupiedPlace());
		final PackagePlacement p2 = preparePlacement(5, 10, 10, 10);
		placements.add(p2);
		assertEquals(50, new Solution(placements).getPurpose()
		                                         .getOccupiedPlace());
		final PackagePlacement p3 = preparePlacement(10, 10, 0, 0);
		placements.add(p3);
		assertEquals(150, new Solution(placements).getPurpose()
		                                          .getOccupiedPlace());
		final PackagePlacement p4 = preparePlacement(10, 10, 10, 0);
		placements.add(p4);
		assertEquals(250, new Solution(placements).getPurpose()
		                                          .getOccupiedPlace());
	}

	private PackagePlacement preparePlacement(int width, int length, int x, int y) {
		return PackagePlacement.builder()
		                       .pack(Package.builder()
		                                    .host(Host.builder()
		                                              .width(width)
		                                              .length(length)
		                                              .build())
		                                    .canOtherPackageBePlacedOn(true)
		                                    .canBePlacedOnPackage(true)
		                                    .build())
		                       .placement(new Placement(x, y))
		                       .build();
	}
}

package com.witkups.carloading.validation;

import com.witkups.carloading.instance.hosts.Host;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.instance.vehicle.Vehicle;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.Placement;
import com.witkups.carloading.validation.packageplacements.PackagePlacementValidator;
import com.witkups.carloading.validation.packageplacements.PlacementValidationError;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.witkups.carloading.PlacementTestProvider.prepareMediumPackagePlacement;
import static com.witkups.carloading.PlacementTestProvider.prepareSimplePlacement;
import static org.junit.Assert.*;

public class PackagePlacementValidatorTest {
	@Test
	public void isPlacedCorrectSimpleTest() throws Exception {
		final Vehicle vehicle = new Vehicle(40, 40);
		final PackagePlacement firstPlacement = prepareSimplePlacement(10, 10, 0, 0);
		final boolean b = new PackagePlacementValidator(firstPlacement, vehicle).canBePlaced(new ArrayList<>());
		assertTrue(b);

		final PackagePlacement invalidInstance = prepareSimplePlacement(10, 10, 5, 5);
		final boolean canBePlacedWhenShouldNot = new PackagePlacementValidator(invalidInstance, vehicle).canBePlaced(
				Collections.singletonList(firstPlacement));
		assertFalse(canBePlacedWhenShouldNot);
	}

	@Test
	public void isPlacedCorrectFlatTest() {
		final Vehicle vehicle = new Vehicle(10, 10);
		final boolean canBePlacedOn = false;
		final PackagePlacement p1 =
				prepareMediumPackagePlacement(5, 5, 5, 0, 0, 5, canBePlacedOn, canBePlacedOn);
		final PackagePlacement p2 =
				prepareMediumPackagePlacement(5, 5, 5, 1, 5, 5, canBePlacedOn, canBePlacedOn);
		final PackagePlacement p3 =
				prepareMediumPackagePlacement(5, 5, 5, 2, 0, 10, canBePlacedOn, canBePlacedOn);
		final PackagePlacement p4 =
				prepareMediumPackagePlacement(5, 5, 5, 3, 5, 10, canBePlacedOn, canBePlacedOn);
		final List<PackagePlacement> packagePlacements = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
		final boolean allPlacedCorrect = PackagePlacementValidator.checkPlacement(packagePlacements, vehicle, false);
		assertTrue(allPlacedCorrect);
		final PackagePlacement invalid =
				prepareMediumPackagePlacement(5, 5, 5, 2, 0, 0, canBePlacedOn, canBePlacedOn);
		packagePlacements.add(invalid);
		final boolean invalidPlacement = PackagePlacementValidator.checkPlacement(packagePlacements, vehicle, false);
		assertFalse("added item's sequence number is lower than can be", invalidPlacement);
		packagePlacements.remove(invalid);
		final boolean correctAfterRemoval = PackagePlacementValidator.checkPlacement(packagePlacements, vehicle, false);
		assertTrue(correctAfterRemoval);
		final PackagePlacement tooHigh =
				prepareMediumPackagePlacement(5, 11, 5, 4, 5, 10, canBePlacedOn, canBePlacedOn);
		packagePlacements.add(tooHigh);
		final boolean tooHighPlacement = PackagePlacementValidator.checkPlacement(packagePlacements, vehicle, false);
		assertFalse("should deny due to pack height", tooHighPlacement);
	}

	@Test
	public void isPlacedCorrectStackTest() {
		final Vehicle vehicle = new Vehicle(10, 10);
		final PackagePlacement p1 =
				prepareMediumPackagePlacement(2, 2, 2, 0, 0, 0, false, true);
		final PackagePlacement p2 =
				prepareMediumPackagePlacement(2, 2, 2, 0, 0, 0, true, true);
		final ArrayList<PackagePlacement> placements = new ArrayList<>(Arrays.asList(p1, p2));
		final boolean firstPlacement = PackagePlacementValidator.checkPlacement(placements, vehicle, false);
		assertTrue(firstPlacement);

		final PackagePlacement p3 =
				prepareMediumPackagePlacement(2, 2, 2, 0, 0, 0, true, true);
		placements.add(p3);
		final boolean secondPlacement = PackagePlacementValidator.checkPlacement(placements, vehicle, false);
		assertTrue(secondPlacement);

		final PackagePlacement p4 =
				prepareMediumPackagePlacement(2, 2, 2, 2, 0, 0, true, true);
		placements.add(p4);
		final boolean placementWithAnotherSequence = PackagePlacementValidator.checkPlacement(placements, vehicle,
		                                                                                      false);
		assertTrue(placementWithAnotherSequence);

		final PackagePlacement p5 =
				prepareMediumPackagePlacement(2, 2, 2, 1, 0, 0, true, true);
		placements.add(p5);
		final boolean placementWithLowerSeqIdOnTheTop =
				PackagePlacementValidator.checkPlacement(placements, vehicle, false);
		assertFalse(placementWithLowerSeqIdOnTheTop);

		placements.remove(p5);
		assertTrue(PackagePlacementValidator.checkPlacement(placements, vehicle, false));
	}

	@Test
	public void isPlacedCorrectStackTest2() {
		final PackagePlacement p1 =
				PackagePlacement.builder()
				                .pack(buildP1Pack())
				                .isPackageReversed(true)
				                .placement(new Placement(0, 0))
				                .build();
		final PackagePlacement p3 =
				PackagePlacement.builder()
				                .pack(buildP3Pack())
				                .isPackageReversed(false)
				                .placement(new Placement(8, 0))
				                .build();
		final List<PackagePlacement> packagePlacements = new ArrayList<>(Arrays.asList(p3, p1));
		final Vehicle vehicle = new Vehicle(28, 26);
		final boolean result = PackagePlacementValidator.checkPlacement(packagePlacements, vehicle, false);
		assertTrue(result);
	}

	private Package buildP3Pack() {
		return Package.builder()
		              .id("p3")
		              .sequenceId(1)
		              .host(Host.builder()
		                        .id("eur2")
		                        .length(12)
		                        .width(10)
		                        .build())
		              .height(12)
		              .canBePlacedOnPackage(false)
		              .canOtherPackageBePlacedOn(true)
		              .build();
	}

	private Package buildP1Pack() {
		return Package.builder()
		              .id("p1")
		              .sequenceId(3)
		              .host(Host.builder()
		                        .id("epa1")
		                        .length(8)
		                        .width(12)
		                        .build())
		              .height(10)
		              .canBePlacedOnPackage(true)
		              .canOtherPackageBePlacedOn(true)
		              .build();
	}

	@Test
	public void corridorAccessTest() {
		final Vehicle vehicle = new Vehicle(10, 10);
		final PackagePlacement p1 = prepareMediumPackagePlacement(2, 2, 2, 0, 2, 0, false, false);
		final PackagePlacement p2 = prepareMediumPackagePlacement(2, 2, 2, 1, 2, 4, false, false);
		final ArrayList<PackagePlacement> placements = new ArrayList<>(Arrays.asList(p1, p2));
		final boolean sameSeqInCorridor = PackagePlacementValidator.checkPlacement(placements, vehicle, false);
		assertTrue(sameSeqInCorridor);
		final PackagePlacement p3 = prepareMediumPackagePlacement(2, 2, 2, 2, 2, 2, false, false);
		placements.add(p3);
		final boolean afterAddingPackageInCorridorWithLowerSeqId = PackagePlacementValidator.checkPlacement(placements,
		                                                                                                    vehicle,
		                                                                                                    false);
		assertFalse(afterAddingPackageInCorridorWithLowerSeqId);
		placements.remove(p3);
		assertTrue(PackagePlacementValidator.checkPlacement(placements, vehicle, false));
		final PackagePlacement p4 = prepareMediumPackagePlacement(2, 2, 2, 2, 3, 2, false, false);
		placements.add(p4);
		final boolean packagePartlyOnTheWayRightSide = PackagePlacementValidator.checkPlacement(placements, vehicle,
		                                                                                        false);
		assertFalse("package on the way to other (right side)", packagePartlyOnTheWayRightSide);
		placements.remove(p4);
		final PackagePlacement p5 = prepareMediumPackagePlacement(2, 2, 2, 2, 1, 2, false, false);
		placements.add(p5);
		final boolean packagePartlyOnTheWayLeftSide = PackagePlacementValidator.checkPlacement(placements, vehicle,
		                                                                                       false);
		assertFalse("package on the way to other (left side)", packagePartlyOnTheWayLeftSide);
		placements.remove(p5);
		final PackagePlacement p6 = prepareMediumPackagePlacement(2, 2, 2, 2, 0, 2, false, false);
		placements.add(p6);
		final boolean cleanWay = PackagePlacementValidator.checkPlacement(placements, vehicle, false);
		assertTrue("added on left side but not disturb corridor", cleanWay);
	}

	@Test
	public void canBePlaced() throws Exception {
		final Vehicle vehicle = new Vehicle(40, 40);
		final PackagePlacement firstPlacement = prepareSimplePlacement(10, 10, 0, 0);
		final boolean placedCorrect = new PackagePlacementValidator(firstPlacement, vehicle).isPlacedCorrect(
				Collections.singletonList(firstPlacement));
		assertTrue(placedCorrect);
		final PackagePlacement invalidInstance = prepareSimplePlacement(10, 10, 5, 5);
		try {
			new PackagePlacementValidator(invalidInstance, vehicle).canBePlaced(
					Collections.singletonList(invalidInstance));
			fail("should throw exception");
		} catch (PlacementValidationError ignored) {}
	}

	@Test
	public void validateTwoPlacedOnOne() {
		final Vehicle vehicle = new Vehicle(10, 10);
		final PackagePlacement base =
				prepareMediumPackagePlacement(10, 2, 10, 0, 0,0, true, true);
		final PackagePlacement left =
				prepareMediumPackagePlacement(5, 2, 5, 0, 0, 0, true, true);
		final PackagePlacement right =
				prepareMediumPackagePlacement(5, 2, 5, 0, 5, 0, true, true);
		final List<PackagePlacement> placement = Arrays.asList(base, left, right);
		final boolean isValid = PackagePlacementValidator.checkPlacement(placement, vehicle, false);
		assertFalse(isValid);
	}

	@Test
	public void validatePlacedOnOtherButOutgoing() {
		final Vehicle vehicle = new Vehicle(10, 10);
		final PackagePlacement base = prepareMediumPackagePlacement(5, 2, 10, 0, 0, 0, true, true);
		final PackagePlacement top = prepareMediumPackagePlacement(10, 2, 5, 0, 0, 0, true, true);
		final List<PackagePlacement> placements = Arrays.asList(base, top);
		final boolean isValid = PackagePlacementValidator.checkPlacement(placements, vehicle, false);
		assertFalse(isValid);
	}
}
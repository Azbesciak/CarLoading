package com.witkups.carloading.instance.vehicle;

import com.witkups.carloading.processing.ParseValidationError;
import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.SectionParser;

import java.util.Optional;

import static com.witkups.carloading.instance.vehicle.VehicleSectionStructure.*;
import static java.lang.Integer.parseInt;

public final class VehicleParser extends SectionParser<Vehicle> {
	public VehicleParser(Section section) {
		super(section);
	}

	@Override
	public Vehicle parse() {
		final Optional<Vehicle> vehicle = section.getSectionStream()
		                                         .peek(line -> validateLength(line, VEHICLE_FIELDS))
		                                         .map(this::prepareVehicle)
		                                         .findFirst();
		return vehicle.orElseThrow(() -> new ParseValidationError("No vehicle data provided"));
	}

	private Vehicle prepareVehicle(String[] sectionLine) {
		return new Vehicle(parseInt(sectionLine[VEHICLE_WIDTH_INDEX]), parseInt(sectionLine[VEHICLE_HEIGHT_INDEX]));
	}
}

package com.witkups.carloading.parser.instance;

import com.witkups.carloading.parser.Section;
import com.witkups.carloading.entity.Vehicle;
import com.witkups.carloading.parser.SectionParser;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class VehicleParser extends SectionParser<Vehicle> {

    private static final int VEHICLE_WIDTH_INDEX = 0;
    private static final int VEHICLE_HEIGHT_INDEX = 1;

    public VehicleParser(Section section) {
        super(section);
    }

    @Override
    public Vehicle parse() {
        final Optional<Vehicle> vehicle = section.getSectionStream()
                .map(this::prepareVehicle)
                .findFirst();
        return vehicle.orElseThrow(
                () -> new IllegalStateException("No vehicle data provided")
        );
    }

    private Vehicle prepareVehicle(String[] sectionLine) {
        return new Vehicle(
                parseInt(sectionLine[VEHICLE_WIDTH_INDEX]),
                parseInt(sectionLine[VEHICLE_HEIGHT_INDEX])
        );
    }
}

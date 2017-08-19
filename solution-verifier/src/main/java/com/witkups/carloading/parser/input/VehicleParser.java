package com.witkups.carloading.parser.input;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.Vehicle;
import com.witkups.carloading.parser.ObjectParser;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class VehicleParser extends ObjectParser<Vehicle> {
    public VehicleParser(InputSection section) {
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
                parseInt(sectionLine[0]),
                parseInt(sectionLine[1])
        );
    }
}

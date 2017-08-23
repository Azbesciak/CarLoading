package com.witkups.carloading.solution.packageplacements;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.Serializer;

import java.util.List;

import static com.witkups.carloading.solution.packageplacements.PackagePlacementsSectionStructure.*;
import static java.lang.String.*;

public final class PackagePlacementsSerializer implements Serializer<Section> {

    private final List<PackagePlacement> placements;

    public PackagePlacementsSerializer(List<PackagePlacement> placements) {
        this.placements = placements;
    }

    @Override
    public Section serialize() {
        final Section section = new Section(placements.size());
        placements.stream()
                .map(this::toStringArray)
                .forEachOrdered(section::add);
        return section;
    }

    private String[] toStringArray(PackagePlacement packagePlacement) {
        final String[] resultRow = new String[PLACEMENT_FIELDS];
        resultRow[PACKAGE_ID_INDEX] = packagePlacement.getPack().getId();
        resultRow[IS_REVERSED_INDEX] = packagePlacement.isPackageReversed() ? "1" : "0";

        final int distanceToLeftEdge = packagePlacement.getPlacement().getDistanceToLeftEdge();
        resultRow[DISTANCE_TO_LEFT_EDGE_INDEX] = valueOf(distanceToLeftEdge);

        final int distanceToVehicleFront = packagePlacement.getPlacement().getDistanceToVehicleFront();
        resultRow[DISTANCE_TO_VEHICLE_FRONT_INDEX] = valueOf(distanceToVehicleFront);
        return resultRow;
    }
}

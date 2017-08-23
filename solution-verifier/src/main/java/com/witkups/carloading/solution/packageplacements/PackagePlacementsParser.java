package com.witkups.carloading.solution.packageplacements;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.instance.packages.Package;
import com.witkups.carloading.processing.SectionParser;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.witkups.carloading.solution.packageplacements.PackagePlacementsSectionStructure.*;
import static java.lang.Integer.*;

public final class PackagePlacementsParser extends SectionParser<List<PackagePlacement>> {

    private final List<Package> packages;

    public PackagePlacementsParser(Section section, List<Package> packages) {
        super(section);
        this.packages = packages;
    }

    @Override
    public List<PackagePlacement> parse() {
        return section.getSectionStream()
                .map(this::preparePackagePlacement)
                .collect(Collectors.toList());
    }

    private PackagePlacement preparePackagePlacement(String[] sectionLine) {
        return PackagePlacement.builder()
                .pack(getPackage(sectionLine))
                .isPackageReversed(isPackageReversed(sectionLine[IS_REVERSED_INDEX]))
                .placement(getPlacement(sectionLine))
                .build();
    }

    private boolean isPackageReversed(String anObject) {
        return "1".equals(anObject);
    }

    private Placement getPlacement(String[] sectionLine) {
        return new Placement(valueOf(sectionLine[DISTANCE_TO_LEFT_EDGE_INDEX]), valueOf(sectionLine[DISTANCE_TO_VEHICLE_FRONT_INDEX]));
    }

    private Package getPackage(String[] sectionLine) {
        return packages.stream()
                .filter(isPackageWithTheSameId(sectionLine[PACKAGE_ID_INDEX]))
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Package " + sectionLine[PACKAGE_ID_INDEX] + " does not exist in instance")
                );
    }

    private Predicate<Package> isPackageWithTheSameId(String id) {
        return pack -> pack.getId().equals(id);
    }
}

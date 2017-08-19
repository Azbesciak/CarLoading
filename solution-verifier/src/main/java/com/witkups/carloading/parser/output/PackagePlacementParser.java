package com.witkups.carloading.parser.output;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.entity.PackagePlacement;
import com.witkups.carloading.parser.ObjectParser;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PackagePlacementParser extends ObjectParser<List<PackagePlacement>> {
    private static final int IS_REVERSED_INDEX = 1;
    private static final int X_POSITION_INDEX = 2;
    private static final int Y_POSITION_INDEX = 3;
    private static final int PACKAGE_ID_INDEX = 0;
    private final List<Package> packages;

    public PackagePlacementParser(InputSection section, List<Package> packages) {
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
        return  PackagePlacement.builder()
                .pack(getPackage(sectionLine))
                .isReversed("1".equals(sectionLine[IS_REVERSED_INDEX]))
                .distanceToLeftEdge(Integer.valueOf(sectionLine[X_POSITION_INDEX]))
                .distanceToVehicleFront(Integer.valueOf(sectionLine[Y_POSITION_INDEX]))
                .build();
    }

    private Package getPackage(String[] sectionLine) {
        return packages.stream()
                .filter(isPackageWithTheSameId(sectionLine[PACKAGE_ID_INDEX]))
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException("Package " + sectionLine[PACKAGE_ID_INDEX] + " does not exist in input")
                );
    }

    private Predicate<Package> isPackageWithTheSameId(String id) {
        return pack -> pack.getId().equals(id);
    }
}

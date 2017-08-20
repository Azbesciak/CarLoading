package com.witkups.carloading.parser.output;

import com.witkups.carloading.parser.Section;
import com.witkups.carloading.entity.Package;
import com.witkups.carloading.entity.PackagePlacement;
import com.witkups.carloading.entity.Placement;
import com.witkups.carloading.parser.SectionParser;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

public class PackagePlacementParser extends SectionParser<List<PackagePlacement>> {
    private static final int IS_REVERSED_INDEX = 1;
    private static final int X_POSITION_INDEX = 2;
    private static final int Y_POSITION_INDEX = 3;
    private static final int PACKAGE_ID_INDEX = 0;
    private final List<Package> packages;

    public PackagePlacementParser(Section section, List<Package> packages) {
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
                .isPackageReversed(isPackageReversed(sectionLine[IS_REVERSED_INDEX]))
                .placement(getPlacement(sectionLine))
                .build();
    }

    private boolean isPackageReversed(String anObject) {
        return "1".equals(anObject);
    }

    private Placement getPlacement(String[] sectionLine) {
        return new Placement(valueOf(sectionLine[X_POSITION_INDEX]), valueOf(sectionLine[Y_POSITION_INDEX]));
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

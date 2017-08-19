package com.witkups.carloading.parser.output;

import com.witkups.carloading.InputReader;
import com.witkups.carloading.Section;
import com.witkups.carloading.entity.PackagePlacement;
import com.witkups.carloading.entity.Purpose;
import com.witkups.carloading.parser.Parser;
import com.witkups.carloading.parser.input.Instance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SolutionParser implements Parser<Solution> {

    private static final int PACKAGE_PLACEMENT_SECTION_INDEX = 1;
    private static final int PURPOSE_SECTION_INDEX = 0;
    private List<Section> sections;
    private final Instance instance;

    public SolutionParser(InputStream solutionInputStream, Instance instance) {
        this.instance = instance;
        this.sections = new InputReader(2, solutionInputStream).read();
    }

    @Override
    public Solution parse() {
        final Purpose purpose = getPurpose();
        final List<PackagePlacement> packagePlacements = getPlacementOfParcels();
        return new Solution(purpose, packagePlacements);
    }

    private Purpose getPurpose() {
        return new PurposeParser(
                getSection(PURPOSE_SECTION_INDEX)
        ).parse();
    }

    private List<PackagePlacement> getPlacementOfParcels() {
        return new PackagePlacementParser(
                getSection(PACKAGE_PLACEMENT_SECTION_INDEX),
                instance.getPackages()
        ).parse();
    }

    private Section getSection(int index) {
        return sections.get(index);
    }
}

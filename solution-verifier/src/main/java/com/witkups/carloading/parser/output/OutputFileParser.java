package com.witkups.carloading.parser.output;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.PackagePlacement;
import com.witkups.carloading.entity.Purpose;
import com.witkups.carloading.parser.FileParser;
import com.witkups.carloading.parser.input.InputParseResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputFileParser extends FileParser<OutputParseResult> {

    private static final int PACKAGE_PLACEMENT_SECTION_INDEX = 1;
    private static final int PURPOSE_SECTION_INDEX = 0;
    private final InputParseResult inputParseResult;

    public OutputFileParser(String filePath, InputParseResult inputParseResult) throws IOException {
        super(2, filePath);
        this.inputParseResult = inputParseResult;
    }

    @Override
    public OutputParseResult parse() {
        final Purpose purpose = getPurpose();
        final List<PackagePlacement> packagePlacements = getPlacementOfParcels();
        return new OutputParseResult(purpose, packagePlacements);
    }

    private Purpose getPurpose() {
        return new PurposeParser(
                getSection(PURPOSE_SECTION_INDEX)
        ).parse();
    }

    private List<PackagePlacement> getPlacementOfParcels() {
        return new PackagePlacementParser(
                getSection(PACKAGE_PLACEMENT_SECTION_INDEX),
                inputParseResult.getPackages()
        ).parse();
    }

    private InputSection getSection(int index) {
        return data.get(index);
    }
}

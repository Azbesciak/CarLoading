package com.witkups.carloading.solution;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.Serializer;
import com.witkups.carloading.solution.packageplacements.PackagePlacement;
import com.witkups.carloading.solution.packageplacements.PackagePlacementsSerializer;
import com.witkups.carloading.solution.purpose.Purpose;
import com.witkups.carloading.solution.purpose.PurposeSerializer;

import java.util.ArrayList;
import java.util.List;

import static com.witkups.carloading.solution.SolutionSectionStructure.*;

public class SolutionSerializer implements Serializer<List<Section>> {

    private final List<PackagePlacement> placements;
    private final Purpose purpose;

    public SolutionSerializer(Solution solution) {
        this.placements = solution.getPackagePlacements();
        this.purpose = solution.getPurpose();
    }

    @Override
    public List<Section> serialize() {
        final Section placementsSection = new PackagePlacementsSerializer(placements).serialize();
        final Section purposeSection = new PurposeSerializer(purpose).serialize();
        return prepareResult(placementsSection, purposeSection);
    }

    private List<Section> prepareResult(Section placementsSection, Section purposeSection) {
        final List<Section> sections = new ArrayList<>(SOLUTION_SECTIONS);
        sections.add(PURPOSE_SECTION_INDEX, purposeSection);
        sections.add(PACKAGE_PLACEMENT_SECTION_INDEX, placementsSection);
        return sections;
    }

}

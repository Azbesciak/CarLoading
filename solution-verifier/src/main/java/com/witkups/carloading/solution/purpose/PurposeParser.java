package com.witkups.carloading.solution.purpose;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.SectionParser;

import java.util.Optional;

import static java.lang.Integer.*;

public class PurposeParser extends SectionParser<Purpose> {

    private static final int MAX_DISTANCE_INDEX = 0;
    private static final int OCCUPIED_PLACE_INDEX = 1;

    public PurposeParser(Section section) {
        super(section);
    }

    @Override
    public Purpose parse() {
        final Optional<Purpose> purpose = section.getSectionStream()
                .map(this::preparePurpose)
                .findFirst();

        return purpose.orElseThrow(() -> new IllegalStateException("Purpose function not found"));
    }

    private Purpose preparePurpose(String[] sectionLine) {
        return new Purpose(
                parseInt(sectionLine[MAX_DISTANCE_INDEX]),
                parseInt(sectionLine[OCCUPIED_PLACE_INDEX])
        );
    }

}

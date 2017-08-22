package com.witkups.carloading.parser.solution;

import com.witkups.carloading.parser.Sectionizer;
import com.witkups.carloading.parser.Section;
import com.witkups.carloading.entity.Purpose;
import com.witkups.carloading.parser.SectionParser;

import java.util.Optional;

import static java.lang.Integer.*;

public class PurposeParser extends SectionParser<Purpose> implements Sectionizer<Purpose> {

    private static final int MAX_DISTANCE_INDEX = 0;
    private static final int OCCUPIED_PLACE_INDEX = 1;

    public PurposeParser(Section section) {
        super(section);
    }

    //TODO
    public PurposeParser() {
        super(new Section(0));
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

    private String[] getAsString(Purpose purpose) {
        return new String[]{String.valueOf(purpose.getMaxDistance()), String.valueOf(purpose.getOccupiedPlace())};
    }

    @Override
    public Section toSection(Purpose purpose) {
        final Section section = new Section(1);
        section.add(getAsString(purpose));
        return section;
    }
}

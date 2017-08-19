package com.witkups.carloading.parser.output;

import com.witkups.carloading.InputSection;
import com.witkups.carloading.entity.Purpose;
import com.witkups.carloading.parser.ObjectParser;

import java.util.Optional;

import static java.lang.Integer.*;

public class PurposeParser extends ObjectParser<Purpose> {

    private static final int MAX_DISTANCE_INDEX = 0;
    private static final int OCCUPIED_PLACE_INDEX = 1;

    public PurposeParser(InputSection section) {
        super(section);
    }

    @Override
    public Purpose parse() {
        final Optional<Purpose> purpose = section.getSectionStream()
                .map(this::preparePurpose)
                .findFirst();

        return purpose.orElseThrow(() -> new IllegalStateException("Purpose function not found"));
    }

    private Purpose preparePurpose(String[] strings) {
        return new Purpose(
                parseInt(strings[MAX_DISTANCE_INDEX]),
                parseInt(strings[OCCUPIED_PLACE_INDEX])
        );
    }
}

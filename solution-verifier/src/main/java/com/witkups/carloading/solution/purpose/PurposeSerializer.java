package com.witkups.carloading.solution.purpose;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.Serializer;

public class PurposeSerializer implements Serializer<Section> {

    private final Purpose purpose;

    public PurposeSerializer(Purpose purpose) {
        this.purpose = purpose;
    }

    @Override
    public Section serialize() {
        final Section section = new Section(1);
        section.add(getAsString(purpose));
        return section;
    }


    private String[] getAsString(Purpose purpose) {
        return new String[]{String.valueOf(purpose.getMaxDistance()), String.valueOf(purpose.getOccupiedPlace())};
    }


}

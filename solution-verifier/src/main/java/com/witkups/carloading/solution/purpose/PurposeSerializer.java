package com.witkups.carloading.solution.purpose;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.Serializer;
import lombok.AllArgsConstructor;

import static com.witkups.carloading.solution.purpose.PurposeSectionStructure.*;
import static java.lang.String.*;

@AllArgsConstructor
public final class PurposeSerializer implements Serializer<Section> {
	private final Purpose purpose;

	@Override
	public Section serialize() {
		final Section section = new Section(1);
		section.add(getAsString(purpose));
		return section;
	}

	private String[] getAsString(Purpose purpose) {
		final String[] line = new String[PURPOSE_FIELDS];
		line[MAX_DISTANCE_INDEX] = valueOf(purpose.getMaxDistance());
		line[OCCUPIED_PLACE_INDEX] = valueOf(purpose.getOccupiedPlace());
		return line;
	}
}

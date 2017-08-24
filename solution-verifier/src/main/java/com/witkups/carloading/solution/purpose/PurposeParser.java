package com.witkups.carloading.solution.purpose;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.processing.SectionParser;

import java.util.Optional;

import static com.witkups.carloading.solution.purpose.PurposeSectionStructure.*;
import static java.lang.Integer.parseInt;

public final class PurposeParser extends SectionParser<Purpose> {
	public PurposeParser(Section section) {
		super(section);
	}

	@Override
	public Purpose parse() {
		final Optional<Purpose> purpose = section.getSectionStream()
		                                         .peek(line -> validateLength(line, PURPOSE_FIELDS))
		                                         .map(this::preparePurpose)
		                                         .findFirst();

		return purpose.orElseThrow(() -> new IllegalStateException("Purpose function not found"));
	}

	private Purpose preparePurpose(String[] sectionLine) {
		return new Purpose(parseInt(sectionLine[MAX_DISTANCE_INDEX]), parseInt(sectionLine[OCCUPIED_PLACE_INDEX]));
	}
}

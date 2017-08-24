package com.witkups.carloading.processing;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class SectionParser<T> implements Parser<T> {
	private final static String LINE_LENGTH_ERROR_FORMAT = "error while parsing line '%s', expected length: %d";
	protected final Section section;

	protected void validateLength(String[] line, int expectedLength) {
		if (line.length != expectedLength) {
			final String message = String.format(LINE_LENGTH_ERROR_FORMAT,
			                                     Section.buildSectionLine(line),
			                                     expectedLength);
			throw new ParseValidationError(message);
		}
	}
}

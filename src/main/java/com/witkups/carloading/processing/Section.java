package com.witkups.carloading.processing;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.valueOf;

@EqualsAndHashCode
public class Section {
	private String[][] content;
	private int currentContentSize;

	public Section(int sectionLength) {
		content = new String[sectionLength][];
		currentContentSize = 0;
	}

	public void add(String[] contentLine) {
		if (currentContentSize < content.length) {
			content[currentContentSize++] = contentLine;
		} else {
			throw new IllegalStateException("More elements than declared!");
		}
	}

	public Stream<String[]> getSectionStream() {
		return Arrays.stream(content, 0, currentContentSize);
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder(valueOf(content.length)).append(System.lineSeparator());
		Arrays.stream(content)
		      .map(Section::buildSectionLine)
		      .forEach(result::append);

		return result.toString();
	}

	public static String buildSectionLine(String[] line) {
		return String.join("\t", line) + System.lineSeparator();
	}

	public static String stringifySections(List<Section> sections) {
		final StringBuilder result = new StringBuilder();
		sections.forEach(result::append);
		return result.toString();
	}
}

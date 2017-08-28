package com.witkups.carloading.processing.reader;

import com.witkups.carloading.processing.Section;
import com.witkups.carloading.validation.ValidationError;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public final class InputReader implements Reader<List<Section>> {
	private final InputStream inputStream;
	private int sectionsToRead;
	private Scanner scanner;

	public InputReader(int sectionsToRead, InputStream inputStream) {
		this.sectionsToRead = sectionsToRead;
		this.inputStream = inputStream;
	}

	public List<Section> read() {
		scanner = new Scanner(inputStream);
		List<Section> sections = readSections();
		scanner.close();
		return sections;
	}

	private List<Section> readSections() {
		List<Section> lines = new ArrayList<>(sectionsToRead);
		while (sectionsToRead > 0) {
			lines.add(readSection());
			sectionsToRead--;
		}
		return lines;
	}

	private Section readSection() {
		final String[] sectionBeginning = readInputLine();
		if (sectionBeginning.length == 1) {
			int sectionLength = parseInt(sectionBeginning[0]);
			Section section = new Section(sectionLength);
			while (sectionLength > 0) {
				section.add(readInputLine());
				sectionLength--;
			}
			return section;
		}
		throw new ValidationError("Expected section, but not provided (sections to read: " + sectionsToRead + ")");
	}

	private String[] readInputLine() {
		return scanner.nextLine().split("\\s+");
	}
}
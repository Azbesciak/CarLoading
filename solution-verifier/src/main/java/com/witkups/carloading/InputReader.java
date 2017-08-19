package com.witkups.carloading;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.*;

public class InputReader {

    private final int sectionsToRead;
    private final InputStream inputStream;
    private Scanner scanner;

    public InputReader(int sectionsToRead, InputStream inputStream) {
        this.sectionsToRead = sectionsToRead;
        this.inputStream = inputStream;
    }

    public List<InputSection> read() {
        scanner = new Scanner(inputStream);
        List<InputSection> sections = readSections(sectionsToRead);
        scanner.close();
        return sections;
    }

    private List<InputSection> readSections(int sectionsToRead) {
        List<InputSection> lines = new ArrayList<>();
        while (sectionsToRead > 0) {
            lines.add(readSection());
            sectionsToRead--;
        }
        return lines;
    }

    private InputSection readSection() {
        final String[] sectionBeginning = readInputLine();
        if (sectionBeginning.length == 1) {
            int sectionLength = parseInt(sectionBeginning[0]);
            InputSection section = new InputSection(sectionLength);
            while (sectionLength > 0) {
                section.add(readInputLine());
                sectionLength--;
            }
            return section;
        }
        return new InputSection(0);
    }

    private String[] readInputLine() {
        return scanner.nextLine().split("\\s+");
    }

}

package com.witkups.carloading.parser;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.stream.Stream;

@ToString
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

}

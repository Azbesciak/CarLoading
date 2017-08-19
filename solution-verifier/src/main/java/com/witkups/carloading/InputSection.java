package com.witkups.carloading;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
public class InputSection {
    private String[][] content;
    private int currentContentSize;

    public InputSection(int sectionLength) {
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

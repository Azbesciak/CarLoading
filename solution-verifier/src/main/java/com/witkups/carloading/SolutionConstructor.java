package com.witkups.carloading;

import com.witkups.carloading.parser.input.InputFileParser;
import com.witkups.carloading.parser.input.InputParseResult;

import java.io.*;
import java.util.*;


public class SolutionConstructor {

    public static void main(String[] args) throws IOException {
        int sectionsToRead = 3;
        List<InputSection> sections = new InputReader(sectionsToRead, System.in).read();
        sections.forEach(section -> section.getSectionStream().forEach(arr -> System.out.println(Arrays.toString(arr))));
        final InputParseResult parse = new InputFileParser("src/test/resources/input-file.txt").parse();

    }

}

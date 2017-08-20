package com.witkups.carloading;

import com.witkups.carloading.parser.reader.FileReader;
import com.witkups.carloading.parser.reader.InputReader;
import com.witkups.carloading.parser.Section;
import com.witkups.carloading.parser.input.InstanceParser;
import com.witkups.carloading.parser.input.Instance;

import java.io.*;
import java.util.*;


public class SolutionConstructor {

    public static void main(String[] args) throws IOException {
        int sectionsToRead = 3;
        List<Section> sections = new InputReader(sectionsToRead, System.in).read();
        sections.forEach(section -> section.getSectionStream().forEach(arr -> System.out.println(Arrays.toString(arr))));
        final InputStream inputStream = FileReader.read("src/test/resources/input-file.txt");
        final Instance parse = new InstanceParser(inputStream).parse();

    }

}

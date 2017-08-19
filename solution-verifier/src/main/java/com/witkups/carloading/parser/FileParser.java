package com.witkups.carloading.parser;

import com.witkups.carloading.InputReader;
import com.witkups.carloading.InputSection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public abstract class FileParser<T> implements Parser<T> {
    protected final List<InputSection> data;

    public FileParser(int sectionsToRead, String filePath) throws IOException {

        try (final FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
            data = new InputReader(sectionsToRead, fileInputStream).read();
        }
    }

}

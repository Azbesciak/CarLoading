package com.witkups.carloading.parser;

import com.witkups.carloading.InputSection;

public abstract class ObjectParser<T> implements Parser<T> {

    private int linesToRead;
    protected InputSection section;

    public ObjectParser(InputSection section) {
        this.section = section;
    }
}

package com.witkups.carloading.parser;

import com.witkups.carloading.Section;

public abstract class SectionParser<T> implements Parser<T> {

    protected Section section;

    public SectionParser(Section section) {
        this.section = section;
    }
}

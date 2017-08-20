package com.witkups.carloading.parser;

public abstract class SectionParser<T> implements Parser<T> {

    protected Section section;

    public SectionParser(Section section) {
        this.section = section;
    }
}

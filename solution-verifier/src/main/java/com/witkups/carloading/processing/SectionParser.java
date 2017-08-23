package com.witkups.carloading.processing;

public abstract class SectionParser<T> implements Parser<T> {

    protected Section section;

    public SectionParser(Section section) {
        this.section = section;
    }
}

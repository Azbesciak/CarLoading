package com.witkups.carloading.parser;

public interface Sectionizer<T> {
    Section toSection(T t);
}

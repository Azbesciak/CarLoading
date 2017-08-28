package com.witkups.carloading.processing;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public abstract class PropertiesLoader<T> {
    private final String constraintsFileName;
    private final Properties prop;

    public PropertiesLoader(String constraintsFileName) {
        this.prop = new Properties();
        this.constraintsFileName = constraintsFileName;
    }

    public T load() {
        try (InputStream input = getInputStream()) {
            prop.load(input);
        } catch (Exception e) {
            System.err.println("No property file was found, used default");
        }
        return build();
    }

    private InputStream getInputStream() {
        return PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(constraintsFileName);
    }

    protected abstract T build();

    protected int getIntProp(String property, String defaultValue) {
        return parseInt(prop.getProperty(property, defaultValue));
    }
}

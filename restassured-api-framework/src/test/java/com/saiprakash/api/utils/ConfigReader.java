package com.saiprakash.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Reads values from src/test/resources/config.properties. */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        return PROPS.getProperty(key);
    }

    public static long getLong(String key) {
        return Long.parseLong(PROPS.getProperty(key));
    }
}

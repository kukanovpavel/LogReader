package ru.kukanov.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.Properties;

/**
 * Created by Pkukanov on 22.12.2016.
 */
public  class ReadConfig {

    private static String TYPE_WEB_SERVICE;

    public String getPropValues() {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
            }

            TYPE_WEB_SERVICE = prop.getProperty("TYPE_WEB_SERVICE");
            return TYPE_WEB_SERVICE;

        } catch (Exception e) {
            System.out.println("Exception: " + e);

        }
        return TYPE_WEB_SERVICE;
    }


}

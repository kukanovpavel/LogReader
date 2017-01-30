package ru.kukanov.logwebservice.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Pkukanov on 22.12.2016.
 */
public  class ReadConfig {

   private Properties prop = new Properties();

    public ReadConfig() {

        try {
            String propFileName = "config.properties";

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);

        }



        }

    public  String getDirectoryResultFile() {

        return prop.getProperty("DIRECTORY_RESULT_FILE");

    }


}

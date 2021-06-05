package com.codecool.report.config;

import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor
public class PropertyReader {

    private static final String APPLICATION_PROPERTY_FILE_PATH = "src/main/resources/application.properties";

    private Properties getPropertyFile() {
        Properties appProps = new Properties();

        try {
            appProps.load(new FileInputStream(APPLICATION_PROPERTY_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appProps;
    }

    public String getDatabaseName() { return getPropertyFile().getProperty("database"); }

    public String getUsername() { return getPropertyFile().getProperty("username"); }

    public String getPassword() { return getPropertyFile().getProperty("password"); }
}

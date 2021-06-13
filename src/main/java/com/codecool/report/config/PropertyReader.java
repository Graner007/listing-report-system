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

    protected String getDatabaseName() { return getPropertyFile().getProperty("database"); }

    protected String getDatabaseUsername() { return getPropertyFile().getProperty("database-username"); }

    protected String getDatabasePassword() { return getPropertyFile().getProperty("database-password"); }

    protected String getFtpServerHost() { return getPropertyFile().getProperty("ftp-host"); }

    protected String getFtpServerUsername() { return getPropertyFile().getProperty("ftp-username"); }

    protected String getFtpServerPassword() { return getPropertyFile().getProperty("ftp-password"); }

    public String getPlantApiUrl() { return getPropertyFile().getProperty("plant-api-url"); }

    public String getMarketplaceApiUrl() { return getPropertyFile().getProperty("marketplace-api-url"); }

    public String getLocationApiUrl() { return getPropertyFile().getProperty("location-api-url"); }

    public String getListingStatusApiUrl() { return getPropertyFile().getProperty("listing-status-api-url"); }
}

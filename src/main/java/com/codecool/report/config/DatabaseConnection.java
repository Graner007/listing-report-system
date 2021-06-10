package com.codecool.report.config;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private final PropertyReader propertyReader = new PropertyReader();

    public Connection connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(propertyReader.getDatabaseName());
        dataSource.setUser(propertyReader.getUsername());
        dataSource.setPassword(propertyReader.getPassword());

        return dataSource.getConnection();
    }
}

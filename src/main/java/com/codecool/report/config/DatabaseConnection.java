package com.codecool.report.config;

import lombok.AllArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class DatabaseConnection {

    private String database;
    private String user;
    private String password;

    public Connection connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        return dataSource.getConnection();
    }
}

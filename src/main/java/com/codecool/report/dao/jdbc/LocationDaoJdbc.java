package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.model.Location;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class LocationDaoJdbc implements LocationDao {

    private final Connection conn;

    @Override
    public void add(Location location) {
        try {
            String sql = "INSERT INTO location (id, manager_name, phone, address_primary, address_secondary, country," +
                    "town, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, location.getId());
            statement.setString(2, location.getManagerName());
            statement.setString(3, location.getPhone());
            statement.setString(4, location.getAddressPrimary());
            statement.setString(5, location.getAddressSecondary());
            statement.setString(6, location.getCountry());
            statement.setString(7, location.getTown());
            statement.setString(8, location.getPostalCode());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Location find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public List<Location> getAll() {
        return null;
    }
}

package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.model.Location;
import com.codecool.report.model.status.Status;
import com.codecool.report.model.status.StatusName;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.List;
import java.util.UUID;

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
    public Location find(UUID id) {
        return null;
    }

    @Override
    public boolean isExist(UUID id) {
        try {
            String sql = "SELECT EXISTS (SELECT id FROM location WHERE id = ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return false;
            if (!rs.getBoolean(1))
                return false;
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void removeAll() {

    }

    @Override
    public List<Location> getAll() {
        return null;
    }

    @Override
    public void update(Location location) {
        try {
            String sql = "UPDATE location SET manager_name = ?, phone = ?, address_primary = ?, address_secondary = ?, country = ?, town = ?, postal_code WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, location.getManagerName());
            statement.setString(2, location.getPhone());
            statement.setString(3, location.getAddressPrimary());
            statement.setString(4, location.getAddressSecondary());
            statement.setString(5, location.getCountry());
            statement.setString(6, location.getTown());
            statement.setString(7, location.getPostalCode());
            statement.setObject(8, location.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

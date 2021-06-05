package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.PlantDao;
import com.codecool.report.model.Plant;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class PlantDaoJdbc implements PlantDao {

    private final Connection conn;

    @Override
    public void add(Plant plant) {
        try {
            String sql = "INSERT INTO plant (id, title, description, location_id, listing_price, currency," +
                    " quantity, status_id, marketplace, upload_time, owner_email_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, plant.getTitle());
            statement.setString(2, plant.getDescription());
            statement.setString(3, String.valueOf(plant.getLocation().getId()));
            statement.setDouble(4, plant.getListingPrice());
            statement.setString(5, plant.getCurrency());
            statement.setInt(6, plant.getQuantity());
            statement.setLong(7, plant.getStatus().getId());
            statement.setLong(8, plant.getMarketplace().getId());
            statement.setDate(9, Date.valueOf(plant.getUploadTime()));
            statement.setString(10, plant.getOwnerEmailAddress());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Plant find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public List<Plant> getAll() {
        return null;
    }
}

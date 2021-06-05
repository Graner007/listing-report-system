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
            String sql = "INSERT INTO plant (id, title, description, inventory_item_location_id, listing_price, currency," +
                    " quantity, listing_status, marketplace, upload_time, owner_email_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, plant.getId());
            statement.setString(2, plant.getTitle());
            statement.setString(3, plant.getDescription());
            statement.setString(4, String.valueOf(plant.getLocation().getId()));
            statement.setDouble(5, plant.getListingPrice());
            statement.setString(6, plant.getCurrency());
            statement.setInt(7, plant.getQuantity());
            statement.setInt(8, plant.getStatus().getId());
            statement.setInt(9, plant.getMarketplace().getId());
            statement.setDate(10, Date.valueOf(plant.getUploadTime()));
            statement.setString(11, plant.getOwnerEmailAddress());
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

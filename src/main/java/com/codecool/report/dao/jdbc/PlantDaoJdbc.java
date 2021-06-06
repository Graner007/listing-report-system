package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.PlantDao;
import com.codecool.report.model.Plant;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
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
            statement.setObject(4, plant.getLocationId());
            statement.setDouble(5, plant.getListingPrice());
            statement.setString(6, plant.getCurrency());
            statement.setLong(7, plant.getQuantity());
            statement.setLong(8, plant.getStatusId());
            statement.setLong(9, plant.getMarketplaceId());
            statement.setDate(10, plant.getUploadTime());
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
    public void removeAll() {

    }

    @Override
    public List<Plant> getAll() {
        return null;
    }
}

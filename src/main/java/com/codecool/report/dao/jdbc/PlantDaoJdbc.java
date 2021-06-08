package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.PlantDao;
import com.codecool.report.model.Plant;
import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.marketplace.MarketplaceName;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
            statement.setInt(7, plant.getQuantity());
            statement.setInt(8, plant.getStatusId());
            statement.setInt(9, plant.getMarketplaceId());
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

    @Override
    public int getTotalCount() {
        try {
            String sql = "SELECT COUNT(id) FROM plant";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (!rs.next())
                return 0;
            return rs.getInt(1) - 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalMarketplaceCountById(int marketplaceId) {
        try {
            String sql = "SELECT COUNT(id) FROM plant WHERE plant.marketplace = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, marketplaceId);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return 0;
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalMarketplacePriceById(int marketplaceId) {
        try {
            String sql = "SELECT SUM(listing_price) FROM plant WHERE plant.marketplace = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, marketplaceId);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return 0;
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getAverageMarketplacePriceById(int marketplaceId) {
        try {
            String sql = "SELECT AVG(listing_price) FROM plant WHERE plant.marketplace = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, marketplaceId);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return 0;
            double formattedResult = Double.parseDouble(String.format("%.2f", rs.getDouble(1)));
            return formattedResult;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String bestEmailLister() {
        try {
            String sql = "SELECT owner_email_address FROM plant GROUP BY owner_email_address LIMIT 1";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return null;
            return rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

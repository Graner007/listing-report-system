package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.PlantDao;
import com.codecool.report.model.Plant;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            String sql = "DELETE FROM plant";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public int getTotalMarketplaceCountByName(String marketplaceName) {
        try {
            String sql = "SELECT COUNT(p.id)\n" +
                    "FROM plant AS p\n" +
                    "INNER JOIN marketplace AS m ON m.id = p.marketplace\n" +
                    "WHERE m.marketplace_name = ?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, marketplaceName);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return 0;
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalMarketplacePriceByName(String marketplaceName) {
        try {
            String sql = "SELECT SUM(p.listing_price)\n" +
                    "FROM plant AS p\n" +
                    "INNER JOIN marketplace AS m ON m.id = p.marketplace\n" +
                    "WHERE m.marketplace_name = ?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, marketplaceName);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return 0;
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getAverageMarketplacePriceByName(String marketplaceName) {
        try {
            String sql = "SELECT AVG(p.listing_price)\n" +
                    "FROM plant AS p\n" +
                    "INNER JOIN marketplace AS m ON m.id = p.marketplace\n" +
                    "WHERE m.marketplace_name = ?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, marketplaceName);
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

    @Override
    public Map<String, Integer> getTotalMarketplaceCountByNameMonthly(String marketplaceName) {
        try {
            String sql = "SELECT\n" +
                    "    CONCAT(date_trunc('month', p.upload_time)::date, ':',\n" +
                    "    (date_trunc('month', p.upload_time) + interval '1 month -1 day')::date) as date,\n" +
                    "    COUNT(*)\n" +
                    "FROM plant AS p\n" +
                    "INNER JOIN marketplace m ON m.id = p.marketplace\n" +
                    "WHERE m.marketplace_name = ?\n" +
                    "GROUP BY date_trunc('month', p.upload_time)\n" +
                    "ORDER BY date;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, marketplaceName);
            ResultSet rs = st.executeQuery();
            Map<String, Integer> result = new HashMap<>();
            while (rs.next())
                result.put(rs.getString(1), rs.getInt(2));
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Double> getTotalMarketplacePriceByNameMonthly(String marketplaceName) {
        try {
            String sql = "SELECT\n" +
                    "    CONCAT(date_trunc('month', p.upload_time)::date, ':',\n" +
                    "    (date_trunc('month', p.upload_time) + interval '1 month -1 day')::date) as date,\n" +
                    "    SUM(p.listing_price)\n" +
                    "FROM plant AS p\n" +
                    "INNER JOIN marketplace m ON m.id = p.marketplace\n" +
                    "WHERE m.marketplace_name = ?\n" +
                    "GROUP BY date_trunc('month', p.upload_time)\n" +
                    "ORDER BY date;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, marketplaceName);
            ResultSet rs = st.executeQuery();
            Map<String, Double> result = new HashMap<>();
            while (rs.next())
                result.put(rs.getString(1), rs.getDouble(2));
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Double> getAverageMarketplacePriceByNameMonthly(String marketplaceName) {
        try {
            String sql = "SELECT\n" +
                    "    CONCAT(date_trunc('month', p.upload_time)::date, ':',\n" +
                    "    (date_trunc('month', p.upload_time) + interval '1 month -1 day')::date) as date,\n" +
                    "    AVG(p.listing_price)\n" +
                    "FROM plant AS p\n" +
                    "INNER JOIN marketplace m ON m.id = p.marketplace\n" +
                    "WHERE m.marketplace_name = ?\n" +
                    "GROUP BY date_trunc('month', p.upload_time)\n" +
                    "ORDER BY date;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, marketplaceName);
            ResultSet rs = st.executeQuery();
            Map<String, Double> result = new HashMap<>();
            while (rs.next())
                result.put(rs.getString(1), Double.parseDouble(String.format("%.2f", rs.getDouble(2))));
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> bestEmailListerMonthly() {
        return null;
    }

    @Override
    public void update(Plant plant) {
        try {
            String sql = "UPDATE plant SET title = ?, description = ?, inventory_item_location_id = ?, listing_price = ?, currency = ?," +
                    " quantity = ?, listing_status = ?, marketplace = ?, upload_time = ?, owner_email_address = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, plant.getTitle());
            statement.setString(2, plant.getDescription());
            statement.setObject(3, plant.getLocationId());
            statement.setDouble(4, plant.getListingPrice());
            statement.setString(5, plant.getCurrency());
            statement.setInt(6, plant.getQuantity());
            statement.setInt(7, plant.getStatusId());
            statement.setInt(8, plant.getMarketplaceId());
            statement.setDate(9, plant.getUploadTime());
            statement.setString(10, plant.getOwnerEmailAddress());
            statement.setObject(11, plant.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        try {
            String sql = "SELECT COUNT(id) FROM plant";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return false;
            if (!(rs.getInt(1) == 0))
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

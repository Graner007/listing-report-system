package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.marketplace.MarketplaceName;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class MarketplaceDaoJdbc implements MarketplaceDao {

    private final Connection conn;

    @Override
    public void add(Marketplace marketplace) {
        try {
            String sql = "INSERT INTO marketplace (id, marketplace_name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, marketplace.getId());
            statement.setString(2, marketplace.getMarketplaceName().getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Marketplace find(int id) {
        try {
            String sql = "SELECT id FROM marketplace WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return null;
            Marketplace marketplace = Marketplace.builder()
                    .id(rs.getInt(1))
                    .marketplaceName(MarketplaceName.valueOf(rs.getString(2)))
                    .build();
            return marketplace;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading marketplace with id: " + id, e);
        }
    }

    @Override
    public boolean isExist(int id) {
        try {
            String sql = "SELECT EXISTS (SELECT id FROM marketplace WHERE id = ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
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
    public List<Marketplace> getAll() {
        return null;
    }
}

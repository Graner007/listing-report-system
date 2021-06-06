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
            statement.setLong(1, marketplace.getId());
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
        return null;
    }

    @Override
    public boolean isExist(int id) {
        try {
            String sql = "SELECT id, marketplace_name FROM marketplace WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
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

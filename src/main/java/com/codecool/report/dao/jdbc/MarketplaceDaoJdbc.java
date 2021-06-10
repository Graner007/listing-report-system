package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.marketplace.MarketplaceName;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.List;
import java.util.Locale;

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
            String sql = "SELECT id, marketplace_name FROM marketplace WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next())
                return null;
            Marketplace marketplace = Marketplace.builder()
                    .id(rs.getInt(1))
                    .marketplaceName(MarketplaceName.valueOf(rs.getString(2).toUpperCase(Locale.ROOT)))
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
    public boolean isEmpty() {
        try {
            String sql = "SELECT COUNT(id) FROM marketplace";
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

    @Override
    public void removeAll() {
        try {
            String sql = "DELETE FROM marketplace";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Marketplace> getAll() {
        return null;
    }

    @Override
    public int getIdByName(String marketplaceName) {
        try {
            String sql = "SELECT id FROM marketplace WHERE marketplace_name = ?";
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
    public void update(Marketplace marketplace) {
        try {
            String sql = "UPDATE marketplace SET marketplace_name = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, marketplace.getMarketplaceName().getName());
            statement.setInt(2, marketplace.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

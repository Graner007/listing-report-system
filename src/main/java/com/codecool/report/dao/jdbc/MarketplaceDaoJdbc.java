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
            throw new RuntimeException(e);
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
            String sqlForeignKeyDrop = "ALTER TABLE plant DROP CONSTRAINT fk_marketplace_id";
            String sqlDeleteTableContent = "DELETE FROM marketplace";
            PreparedStatement statement1 = conn.prepareStatement(sqlForeignKeyDrop, Statement.RETURN_GENERATED_KEYS);
            statement1.executeUpdate();
            PreparedStatement statement2 = conn.prepareStatement(sqlDeleteTableContent, Statement.RETURN_GENERATED_KEYS);
            statement2.executeUpdate();
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

    @Override
    public void addForeignKey() {
        try {
            String sql = "ALTER TABLE ONLY plant\n" +
                    "    ADD CONSTRAINT fk_marketplace_id FOREIGN KEY (marketplace) REFERENCES marketplace(id);";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

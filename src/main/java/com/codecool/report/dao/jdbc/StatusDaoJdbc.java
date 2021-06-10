package com.codecool.report.dao.jdbc;

import com.codecool.report.dao.StatusDao;
import com.codecool.report.model.status.Status;
import com.codecool.report.model.status.StatusName;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class StatusDaoJdbc implements StatusDao {

    private final Connection conn;

    @Override
    public void add(Status status) {
        try {
            String sql = "INSERT INTO listing_status (id, status_name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, status.getId());
            statement.setString(2, status.getStatusName().getStatus());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Status find(int id) {
        return null;
    }

    @Override
    public boolean isExist(int id) {
        try {
            String sql = "SELECT EXISTS (SELECT id FROM listing_status WHERE id = ?)";
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
            String sql = "SELECT COUNT(id) FROM listing_status";
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

    }

    @Override
    public List<Status> getAll() {
        return null;
    }

    @Override
    public void update(Status status) {
        try {
            String sql = "UPDATE listing_status SET status_name = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, status.getStatusName().getStatus());
            statement.setInt(2, status.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

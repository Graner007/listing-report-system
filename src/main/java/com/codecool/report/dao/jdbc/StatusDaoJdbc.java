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
            statement.setLong(1, status.getId());
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
            String sql = "SELECT id, status_name FROM listing_status WHERE id = ?";
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
    public List<Status> getAll() {
        return null;
    }
}

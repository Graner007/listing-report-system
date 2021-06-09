package com.codecool.report.dao;

import com.codecool.report.model.status.Status;

import java.util.List;

public interface StatusDao {

    void add(Status status);
    Status find(int id);
    boolean isExist(int id);
    void removeAll();
    void update(Status status);

    List<Status> getAll();
}

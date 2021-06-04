package com.codecool.report.dao;

import com.codecool.report.model.status.Status;

import java.util.List;

public interface StatusDao {

    void add(Status status);
    Status find(int id);
    void remove(int id);
    void removeAll();

    List<Status> getAll();
}

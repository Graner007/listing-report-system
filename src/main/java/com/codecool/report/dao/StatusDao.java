package com.codecool.report.dao;

import com.codecool.report.model.status.Status;

public interface StatusDao {

    void add(Status status);
    boolean isExist(int id);
    boolean isEmpty();
    void removeAll();
    void update(Status status);
    void addForeignKey();
}

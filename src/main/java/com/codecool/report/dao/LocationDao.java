package com.codecool.report.dao;

import com.codecool.report.model.Location;

import java.util.UUID;

public interface LocationDao {

    void add(Location location);
    boolean isExist(UUID id);
    boolean isEmpty();
    void removeAll();
    void update(Location location);
    void addForeignKey();
}

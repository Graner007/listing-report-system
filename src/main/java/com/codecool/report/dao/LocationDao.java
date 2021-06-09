package com.codecool.report.dao;

import com.codecool.report.model.Location;

import java.util.List;
import java.util.UUID;

public interface LocationDao {

    void add(Location location);
    Location find(UUID id);
    boolean isExist(UUID id);
    void removeAll();
    void update(Location location);

    List<Location> getAll();
}

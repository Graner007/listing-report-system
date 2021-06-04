package com.codecool.report.dao;

import com.codecool.report.model.Location;

import java.util.List;

public interface LocationDao {

    void add(Location location);
    Location find(int id);
    void remove(int id);
    void removeAll();

    List<Location> getAll();
}

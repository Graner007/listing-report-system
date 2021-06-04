package com.codecool.report.dao;

import com.codecool.report.model.Plant;

import java.util.List;

public interface PlantDao {

    void add(Plant plant);
    Plant find(int id);
    void remove(int id);
    void removeAll();

    List<Plant> getAll();
}

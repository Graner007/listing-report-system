package com.codecool.report.dao;

import com.codecool.report.model.Plant;

import java.util.List;

public interface PlantDao {

    void add(Plant plant);
    Plant find(int id);
    void removeAll();
    int getTotalCount();
    int getTotalMarketplaceCountById(int id);
    int getTotalEbayPrice(int id);
    double getAverageEbayPrice(int id);

    List<Plant> getAll();
}

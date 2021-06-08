package com.codecool.report.dao;

import com.codecool.report.model.Plant;

import java.util.List;
import java.util.Map;

public interface PlantDao {

    void add(Plant plant);
    Plant find(int id);
    void removeAll();

    int getTotalCount();
    int getTotalMarketplaceCountByName(String name);
    int getTotalMarketplacePriceByName(String name);
    double getAverageMarketplacePriceByName(String name);
    String bestEmailLister();

    Map<String, Integer> getTotalMarketplaceCountByNameMonthly(String name);
    Map<String, Double> getTotalMarketplacePriceByNameMonthly(String name);
    Map<String, Double> getAverageMarketplacePriceByNameMonthly(String name);

    List<Plant> getAll();
}

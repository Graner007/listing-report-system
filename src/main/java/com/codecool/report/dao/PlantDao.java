package com.codecool.report.dao;

import com.codecool.report.model.Plant;

import java.util.List;
import java.util.Map;

public interface PlantDao {

    void add(Plant plant);
    void removeAll();
    void update(Plant plant);
    boolean isEmpty();

    int getTotalCount();
    int getTotalMarketplaceCountByName(String name);
    int getTotalMarketplacePriceByName(String name);
    double getAverageMarketplacePriceByName(String name);
    String bestEmailLister();

    Map<String, Integer> getTotalMarketplaceCountByNameMonthly(String name);
    Map<String, Double> getTotalMarketplacePriceByNameMonthly(String name);
    Map<String, Double> getAverageMarketplacePriceByNameMonthly(String name);
    Map<String, String> bestEmailListerMonthly();
}

package com.codecool.report.dao;

import com.codecool.report.model.marketplace.Marketplace;

import java.util.List;

public interface MarketplaceDao {

    void add(Marketplace marketplace);
    Marketplace find(int id);
    boolean isExist(int id);
    boolean isEmpty();
    void removeAll();
    int getIdByName(String marketplace);
    void update(Marketplace marketplace);

    List<Marketplace> getAll();
}

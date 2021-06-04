package com.codecool.report.dao;

import com.codecool.report.model.marketplace.Marketplace;

import java.util.List;

public interface MarketplaceDao {

    void add(Marketplace marketplace);
    Marketplace find(int id);
    void remove(int id);
    void removeAll();

    List<Marketplace> getAll();
}

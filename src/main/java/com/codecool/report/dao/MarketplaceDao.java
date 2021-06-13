package com.codecool.report.dao;

import com.codecool.report.model.marketplace.Marketplace;

public interface MarketplaceDao {

    void add(Marketplace marketplace);
    Marketplace find(int id);
    boolean isExist(int id);
    boolean isEmpty();
    void removeAll();
    void update(Marketplace marketplace);
    void addForeignKey();
}

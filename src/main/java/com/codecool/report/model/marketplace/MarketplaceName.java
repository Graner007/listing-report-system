package com.codecool.report.model.marketplace;

public enum MarketplaceName {

    EBAY("ebay"),
    AMAZON("amazon");

    private final String name;

    MarketplaceName(String name) { this.name = name; }

    public String getName() { return name; }
}

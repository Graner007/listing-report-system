package com.codecool.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {

    private int totalListingCount;

    private int totalEbayListingCount;

    private int totalEbayListingPrice;

    private double AverageEbayListingPrice;

    private int totalAmazonListingCount;

    private int totalAmazonListingPrice;

    private double AverageAmazonListingPrice;

    private String bestListerEmailAddress;

    private Map<String, Integer> totalEbayListingCountPerMonth;

    private Map<String, Double> totalEbayListingPricePerMonth;

    private Map<String, Double> averageEbayListingPricePerMonth;

    private Map<String, Integer> totalAmazonListingCountPerMonth;

    private Map<String, Double> totalAmazonListingPricePerMonth;

    private Map<String, Double> averageAmazonListingPricePerMonth;

    private Map<String, String> bestListerEmailAddressOfTheMonth;
}

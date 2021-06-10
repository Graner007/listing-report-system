package com.codecool.report;

import com.codecool.report.formatter.JsonOutPutFormatter;
import com.codecool.report.formatter.OutputFormatter;
import com.codecool.report.model.Report;
import com.codecool.report.service.LocationService;
import com.codecool.report.service.MarketplaceService;
import com.codecool.report.service.PlantService;
import com.codecool.report.service.StatusService;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;

import java.io.IOException;

@AllArgsConstructor
public class Operation {

    private final StatusService statusService;
    private final MarketplaceService marketplaceService;
    private final LocationService locationService;
    private final PlantService plantService;

    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_RESET = "\u001B[0m";

    public void fillUpDatabase() throws ParseException, IOException {
        if (statusService.addAllStatus() && marketplaceService.addMarketPlace() && locationService.addAllLocation() && plantService.addAllPlant())
            System.out.println("Database Filled up.");
        else
            System.out.println(TEXT_RED + "Your database is not empty! Please remove or update your database." + TEXT_RESET);
    }

    public void updateDatabase() throws ParseException, IOException {
        statusService.updateAllStatus();
        marketplaceService.updateMarketPlace();
        locationService.updateAllLocation();
        plantService.updateAllPlant();

        System.out.println(TEXT_GREEN + "Database Updated." + TEXT_RESET);
    }

    public Report createReport() throws IOException {
        if (statusService.isStatusEmpty() && marketplaceService.isMarketplaceEmpty() && locationService.isLocationEmpty() && plantService.isPlantEmpty()) {
            System.out.println(TEXT_RED + "Your database is empty! Please fill up your database." + TEXT_RESET);
            return null;
        }

        Report report = new Report();

        report.setTotalListingCount(plantService.getTotalPlantCount());

        //ebay
        report.setTotalEbayListingCount(plantService.getTotalEbayCount());
        report.setTotalEbayListingPrice(plantService.getTotalEbayPrice());
        report.setAverageEbayListingPrice(plantService.getAverageEbayPrice());

        report.setTotalEbayListingCountPerMonth(plantService.getTotalEbayCountMonthly());
        report.setTotalEbayListingPricePerMonth(plantService.getTotalEbayPriceMonthly());
        report.setAverageEbayListingPricePerMonth(plantService.getAverageEbayPriceMonthly());


        //amazon
        report.setTotalAmazonListingCount(plantService.getTotalAmazonCount());
        report.setTotalAmazonListingPrice(plantService.getTotalAmazonPrice());
        report.setAverageAmazonListingPrice(plantService.getAverageAmazonPrice());

        report.setTotalAmazonListingCountPerMonth(plantService.getTotalAmazonCountMonthly());
        report.setTotalAmazonListingPricePerMonth(plantService.getTotalAmazonPriceMonthly());
        report.setAverageAmazonListingPricePerMonth(plantService.getAverageAmazonPriceMonthly());

        //email
        report.setBestListerEmailAddress(plantService.getBestEmailLister());
        report.setBestListerEmailAddressOfTheMonth(null);

        OutputFormatter formatter = new JsonOutPutFormatter();
        formatter.printToFile(report);

        System.out.println(TEXT_GREEN + "Report is made." + TEXT_RESET);
        return report;
    }

    public void exitProgram() {
        System.out.println("See you soon, GoodBye!");
        System.exit(0);
    }
}

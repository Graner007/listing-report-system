package com.codecool.report;

import com.codecool.report.config.FtpServerConnection;
import com.codecool.report.formatter.JsonOutPutFormatter;
import com.codecool.report.formatter.OutputFormatter;
import com.codecool.report.model.Report;
import com.codecool.report.service.LocationService;
import com.codecool.report.service.MarketplaceService;
import com.codecool.report.service.PlantService;
import com.codecool.report.service.StatusService;
import com.codecool.report.util.PrintColor;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;

import java.io.IOException;

@AllArgsConstructor
public class Operation {

    private final StatusService statusService;
    private final MarketplaceService marketplaceService;
    private final LocationService locationService;
    private final PlantService plantService;

    private final FtpServerConnection ftpServerConnection = new FtpServerConnection();
    private final OutputFormatter formatter = new JsonOutPutFormatter();

    public void fillUpDatabase() throws ParseException, IOException {
        if (statusService.addAllStatus() && marketplaceService.addMarketPlace() &&
                locationService.addAllLocation() && plantService.addAllPlant())
            System.out.println(PrintColor.TEXT_GREEN.getUnicode() + "Database Filled up." + PrintColor.TEXT_GREEN.getUnicode());
        else
            System.out.println(PrintColor.TEXT_RED.getUnicode() + "Your database is not empty! Please remove or update your database." + PrintColor.TEXT_RESET.getUnicode());
    }

    public void updateDatabase() throws ParseException, IOException {
        statusService.updateAllStatus();
        marketplaceService.updateMarketPlace();
        locationService.updateAllLocation();
        plantService.updateAllPlant();

        System.out.println(PrintColor.TEXT_GREEN.getUnicode() + "Database Updated." + PrintColor.TEXT_RESET.getUnicode());
    }

    public void removeAndFillUpDatabase() throws ParseException, IOException {
        if (statusService.removeAllStatus() && marketplaceService.removeAllMarketplace()
                && locationService.removeAllLocation() && plantService.removeAllPlant())
            System.out.println(PrintColor.TEXT_GREEN.getUnicode() + "Database removed and filled up." + PrintColor.TEXT_RESET.getUnicode());
        else
            System.out.println(PrintColor.TEXT_RED.getUnicode() + "Your database is empty! Please add or update your database." + PrintColor.TEXT_RESET.getUnicode());
    }

    public void createReport() throws IOException {
        if (statusService.isStatusEmpty() && marketplaceService.isMarketplaceEmpty() && locationService.isLocationEmpty() && plantService.isPlantEmpty()) {
            System.out.println(PrintColor.TEXT_RED.getUnicode() + "Your database is empty! Please fill up your database." + PrintColor.TEXT_RESET.getUnicode());
            return;
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

        formatter.printToFile(report);

        System.out.println(PrintColor.TEXT_GREEN.getUnicode() + "Report is made." + PrintColor.TEXT_RESET.getUnicode());
    }

    public void createReportAndUploadToFtpServer() throws IOException {
        createReport();
        ftpServerConnection.uploadJsonFile();
    }

    public void exitProgram() {
        System.out.println("See you soon, GoodBye!");
        System.exit(0);
    }
}

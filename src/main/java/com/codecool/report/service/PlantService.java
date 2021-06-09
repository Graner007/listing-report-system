package com.codecool.report.service;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.dao.PlantDao;
import com.codecool.report.dao.StatusDao;
import com.codecool.report.formatter.CsvOutputFormatter;
import com.codecool.report.model.ImportLog;
import com.codecool.report.model.Plant;
import com.codecool.report.model.marketplace.MarketplaceName;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class PlantService {

    private final PlantDao plantDao;
    private final LocationDao locationDao;
    private final MarketplaceDao marketplaceDao;
    private final StatusDao statusDao;
    private final ImportLog importLog = new ImportLog();
    private final CsvOutputFormatter csvOutputFormatter = new CsvOutputFormatter();
    private static final String PLANT_API = "https://my.api.mockaroo.com/listing?key=63304c70";

    public Map<String, Double> getAverageAmazonPriceMonthly() { return plantDao.getAverageMarketplacePriceByNameMonthly(MarketplaceName.AMAZON.getName()); }

    public Map<String, Double> getTotalAmazonPriceMonthly() { return plantDao.getTotalMarketplacePriceByNameMonthly(MarketplaceName.AMAZON.getName()); }

    public Map<String, Integer> getTotalAmazonCountMonthly() { return plantDao.getTotalMarketplaceCountByNameMonthly(MarketplaceName.AMAZON.getName()); }

    public Map<String, Double> getAverageEbayPriceMonthly() { return plantDao.getAverageMarketplacePriceByNameMonthly(MarketplaceName.EBAY.getName()); }

    public Map<String, Double> getTotalEbayPriceMonthly() { return plantDao.getTotalMarketplacePriceByNameMonthly(MarketplaceName.EBAY.getName()); }

    public Map<String, Integer> getTotalEbayCountMonthly() { return plantDao.getTotalMarketplaceCountByNameMonthly(MarketplaceName.EBAY.getName()); }

    public String getBestEmailLister() { return plantDao.bestEmailLister(); }

    public double getAverageAmazonPrice() { return plantDao.getAverageMarketplacePriceByName(MarketplaceName.AMAZON.getName()); }

    public int getTotalAmazonPrice() { return plantDao.getTotalMarketplacePriceByName(MarketplaceName.AMAZON.getName()); }

    public int getTotalAmazonCount() { return plantDao.getTotalMarketplaceCountByName(MarketplaceName.AMAZON.getName()); }

    public double getAverageEbayPrice() { return plantDao.getAverageMarketplacePriceByName(MarketplaceName.EBAY.getName()); }

    public int getTotalEbayPrice() { return plantDao.getTotalMarketplacePriceByName(MarketplaceName.EBAY.getName()); }

    public int getTotalEbayCount() { return plantDao.getTotalMarketplaceCountByName(MarketplaceName.EBAY.getName()); }

    public int getTotalPlantCount() { return plantDao.getTotalCount(); }

    public void getAllPlant() throws ParseException, IOException {
        String data = ApiReader.getDataFromApi(PLANT_API);

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            Plant plant = Plant.builder()
                    .id(UUID.fromString((String) obj.get("id")))
                    .title((String) obj.get("title"))
                    .description((String) obj.get("description"))
                    .locationId(UUID.fromString((String) obj.get("location_id")))
                    .listingPrice(Double.parseDouble(String.valueOf(obj.get("listing_price"))))
                    .currency((String) obj.get("currency"))
                    .quantity(Integer.parseInt(String.valueOf(obj.get("quantity"))))
                    .statusId(Integer.parseInt(String.valueOf(obj.get("listing_status"))))
                    .marketplaceId(Integer.parseInt(String.valueOf(obj.get("marketplace"))))
                    .uploadTime(getUploadTime(obj.get("upload_time")))
                    .ownerEmailAddress((String) obj.get("owner_email_address"))
                    .build();

            String result = validatePlantFields(plant);

            if (result.equals(""))
                plantDao.add(plant);
            else {
                importLog.setPlantId(String.valueOf(plant.getId()));
                importLog.setMarketplaceName(marketplaceDao.find(plant.getMarketplaceId()).getMarketplaceName().getName());
                importLog.setInvalidField(result);
                csvOutputFormatter.printToFile(importLog);
            }
        }
    }

    private String validatePlantFields(Plant plant) {
        String wrongField = "";

        if (!checkValueIsNotNull(plant.getId()))
            wrongField = "id";
        else if (!checkValueIsNotNull(plant.getTitle()))
            wrongField = "title";
        else if (!checkValueIsNotNull(plant.getDescription()))
            wrongField = "description";
        else if (!checkValueIsNotNull(plant.getLocationId()) || !locationDao.isExist(plant.getLocationId()))
            wrongField = "location_id";
        else if (!validateListingPrice(plant.getListingPrice()))
            wrongField = "listing_price";
        else if (!validateCurrency(plant.getCurrency()))
            wrongField = "currency";
        else if (!validateQuantity(plant.getQuantity()))
            wrongField = "quantity";
        else if (!checkValueIsNotNull(plant.getStatusId()) || !statusDao.isExist(plant.getStatusId()))
            wrongField = "listing_status";
        else if (!checkValueIsNotNull(plant.getMarketplaceId()) || !marketplaceDao.isExist(plant.getMarketplaceId()))
            wrongField = "marketplace";
        else if (!validateEmail(plant.getOwnerEmailAddress()))
            wrongField = "owner_email_address";
        return wrongField;
    }

    private Date getUploadTime(Object time) {
        if (time != null) {
            String[] uploadTime = String.valueOf(time).split("/");
            int year = Integer.parseInt(uploadTime[2]);
            int month = Integer.parseInt(uploadTime[0]);
            int day = Integer.parseInt(uploadTime[1]);
            return Date.valueOf(LocalDate.of(year, month, day));
        }

        return null;
    }

    private boolean checkValueIsNotNull(Object value) { return value != null; }

    private boolean validateListingPrice(double listingPrice) {
        String[] value = String.valueOf(listingPrice).split("\\.");
        return Integer.parseInt(value[0]) > 0 && value[1].length() == 2;
    }

    private boolean validateCurrency(String currency) {
        if (currency != null)
            return currency.length() == 3;

        return false;
    }

    private boolean validateQuantity(int quantity) { return quantity > 0; }

    private boolean validateEmail(String email) {
        if (email != null)
            return email.contains("@");

        return false;
    }
}

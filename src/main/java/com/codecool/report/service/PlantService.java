package com.codecool.report.service;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.dao.PlantDao;
import com.codecool.report.dao.StatusDao;
import com.codecool.report.model.Location;
import com.codecool.report.model.Plant;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class PlantService {

    private final PlantDao plantDao;
    private final LocationDao locationDao;
    private final MarketplaceDao marketplaceDao;
    private final StatusDao statusDao;
    private static final String PLANT_API = "https://my.api.mockaroo.com/listing?key=63304c70";

    public void getAllPlant() throws ParseException {
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

            if (plant.getListingPrice() > 0 &&
                    locationDao.isExist(plant.getLocationId()) &&
                    String.valueOf(plant.getListingPrice()).split("\\.")[1].length() == 2 &&
                    plant.getCurrency().length() == 3 &&
                    plant.getQuantity() > 0 &&
                    statusDao.isExist(plant.getStatusId()) &&
                    marketplaceDao.isExist(plant.getMarketplaceId()) &&
                    plant.getOwnerEmailAddress().contains("@")) {
                plantDao.add(plant);
            }
        }
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
}

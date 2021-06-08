package com.codecool.report.service;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.dao.PlantDao;
import com.codecool.report.dao.StatusDao;
import com.codecool.report.formatter.CsvOutputFormatter;
import com.codecool.report.model.Plant;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PlantService {

    private final PlantDao plantDao;
    private final LocationDao locationDao;
    private final MarketplaceDao marketplaceDao;
    private final StatusDao statusDao;
    private final CsvOutputFormatter csvOutputFormatter = new CsvOutputFormatter();
    private static final String PLANT_API = "https://my.api.mockaroo.com/listing?key=63304c70";

    public void getAllPlant() throws ParseException, IOException {
        String data = ApiReader.getDataFromApi(PLANT_API);

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            String[] invalidFields = new String[3];

            if (!checkValueIsNotNull(obj.get("id")))
                invalidFields[2] = "id";
            else if (!checkValueIsNotNull(obj.get("title")))
                invalidFields[2] = "title";
            else if (!checkValueIsNotNull(obj.get("description")))
                invalidFields[2] = "description";
            else if (!checkValueIsNotNull(obj.get("location_id")) || !locationDao.isExist(UUID.fromString(String.valueOf(obj.get("location_id")))))
                invalidFields[2] = "location_id";
            else if (!validateListingPrice(Double.parseDouble(String.valueOf(obj.get("listing_price")))))
                invalidFields[2] = "listing_price";
            else if (!validateCurrency(obj.get("currency")))
                invalidFields[2] = "currency";
            else if (!validateQuantity(obj.get("quantity")))
                invalidFields[2] = "quantity";
            else if (!checkValueIsNotNull(obj.get("listing_status")) || !statusDao.isExist(Integer.parseInt(String.valueOf(obj.get("listing_status")))))
                invalidFields[2] = "listing_status";
            else if (!checkValueIsNotNull(obj.get("marketplace")) || !marketplaceDao.isExist(Integer.parseInt(String.valueOf(obj.get("marketplace")))))
                invalidFields[2] = "marketplace";
            else if (!validateEmail(obj.get("owner_email_address")))
                invalidFields[2] = "owner_email_address";
            else {
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

                plantDao.add(plant);
                continue;
            }

            invalidFields[0] = String.valueOf(obj.get("id"));
            csvOutputFormatter.printToFile(invalidFields);
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

    private boolean checkValueIsNotNull(Object value) { return value != null; }

    private boolean validateListingPrice(Object listingPrice) {
        if (listingPrice != null) {
            String[] value = String.valueOf(listingPrice).split("\\.");
            return Integer.parseInt(value[0]) > 0 && value[1].length() == 2;
        }

        return false;
    }

    private boolean validateCurrency(Object currency) {
        if (currency != null)
            return String.valueOf(currency).length() == 3;

        return false;
    }

    private boolean validateQuantity(Object quantity) {
        if (quantity != null) {
            return Integer.parseInt(String.valueOf(quantity)) > 0;
        }

        return false;
    }

    private boolean validateEmail(Object email) {
        if (email != null)
            return String.valueOf(email).contains("@");

        return false;
    }
}

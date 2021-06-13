package com.codecool.report.service;

import com.codecool.report.config.PropertyReader;
import com.codecool.report.dao.LocationDao;
import com.codecool.report.model.Location;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class LocationService {

    private final LocationDao locationDao;
    private final PropertyReader propertyReader = new PropertyReader();

    private List<Location> downloadAllLocation() throws ParseException {
        String data = ApiReader.getDataFromApi(propertyReader.getLocationApiUrl());
        List<Location> result = new ArrayList<>();

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (Object o : arr) {
            JSONObject obj = (JSONObject) o;

            Location location = Location.builder()
                    .id(UUID.fromString((String) obj.get("id")))
                    .managerName(String.valueOf(obj.get("manager_name")))
                    .phone(String.valueOf(obj.get("phone")))
                    .addressPrimary(String.valueOf(obj.get("address_primary")))
                    .addressSecondary(String.valueOf(obj.get("address_secondary")))
                    .country(String.valueOf(obj.get("country")))
                    .town(String.valueOf(obj.get("town")))
                    .postalCode(String.valueOf(obj.get("postal_code")))
                    .build();

            result.add(location);
        }

        return result;
    }

    public boolean addAllLocation() throws ParseException {
        if (isLocationEmpty()) {
            List<Location> locations = downloadAllLocation();
            locations.forEach(locationDao::add);
            locationDao.addForeignKey();
            return true;
        }
        return false;
    }

    public void updateAllLocation() throws ParseException {
        if (isLocationEmpty())
            addAllLocation();
        else {
            List<Location> locations = downloadAllLocation();
            locations.forEach(locationDao::update);
        }
    }

    public boolean removeAllLocation() throws ParseException {
        if (!isLocationEmpty()) {
            locationDao.removeAll();
            addAllLocation();
            return true;
        }
        return false;
    }

    public boolean isLocationEmpty() { return locationDao.isEmpty(); }
}

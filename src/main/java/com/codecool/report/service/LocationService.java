package com.codecool.report.service;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.model.Location;
import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.marketplace.MarketplaceName;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.UUID;

@AllArgsConstructor
public class LocationService {

    private final LocationDao locationDao;
    private static final String LOCATION_API = "https://my.api.mockaroo.com/location?key=63304c70";

    public void getAllLocation() throws ParseException {
        String data = ApiReader.getDataFromApi(LOCATION_API);

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

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

            locationDao.add(location);
        }
    }
}

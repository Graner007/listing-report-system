package com.codecool.report.service;

import com.codecool.report.dao.StatusDao;
import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.marketplace.MarketplaceName;
import com.codecool.report.model.status.Status;
import com.codecool.report.model.status.StatusName;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@AllArgsConstructor
public class StatusService {

    private final StatusDao statusDao;
    private static final String LISTING_STATUS_API = "https://my.api.mockaroo.com/listingStatus?key=63304c70";

    public void getAllStatuses() throws ParseException {
        String data = ApiReader.getDataFromApi(LISTING_STATUS_API);

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            Status status =Status.builder()
                    .id((long) obj.get("id"))
                    .statusName(StatusName.valueOf((String) obj.get("status_name")))
                    .build();

            statusDao.add(status);
        }
    }
}

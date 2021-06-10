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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
public class StatusService {

    private final StatusDao statusDao;
    private static final String LISTING_STATUS_API = "https://my.api.mockaroo.com/listingStatus?key=63304c70";

    private List<Status> downloadAllStatus() throws ParseException {
        String data = ApiReader.getDataFromApi(LISTING_STATUS_API);
        List<Status> result = new ArrayList<>();

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            Status status =Status.builder()
                    .id(Integer.parseInt(String.valueOf(obj.get("id"))))
                    .statusName(StatusName.valueOf((String) obj.get("status_name")))
                    .build();

            result.add(status);
        }

        return result;
    }

    public boolean addAllStatus() throws ParseException {
        if (isStatusEmpty()) {
            List<Status> statuses = downloadAllStatus();
            statuses.forEach(status -> statusDao.add(status));
            return true;
        }
        return false;
    }

    public void updateAllStatus() throws ParseException {
        if (isStatusEmpty())
            addAllStatus();
        else {
            List<Status> statuses = downloadAllStatus();
            statuses.forEach(status -> statusDao.update(status));
        }
    }

    public boolean isStatusEmpty() { return statusDao.isEmpty(); }


}

package com.codecool.report.service;

import com.codecool.report.config.PropertyReader;
import com.codecool.report.dao.StatusDao;
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
    private final PropertyReader propertyReader = new PropertyReader();

    private List<Status> downloadAllStatus() throws ParseException {
        String data = ApiReader.getDataFromApi(propertyReader.getListingStatusApiUrl());
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
            System.out.println("Filling up database...");
            List<Status> statuses = downloadAllStatus();
            statuses.forEach(status -> statusDao.add(status));
            statusDao.addForeignKey();
            return true;
        }
        return false;
    }

    public void updateAllStatus() throws ParseException {
        if (isStatusEmpty())
            addAllStatus();
        else {
            System.out.println("Updating database...");
            List<Status> statuses = downloadAllStatus();
            statuses.forEach(status -> statusDao.update(status));
        }
    }

    public boolean removeAllStatus() throws ParseException {
        if (!isStatusEmpty()) {
            statusDao.removeAll();
            addAllStatus();
            return true;
        }
        return false;
    }

    public boolean isStatusEmpty() { return statusDao.isEmpty(); }


}

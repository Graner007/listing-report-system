package com.codecool.report.service;

import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.marketplace.MarketplaceName;
import com.codecool.report.util.ApiReader;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MarketplaceService {

    private final MarketplaceDao marketplaceDao;
    private static final String MARKETPLACE_API = "https://my.api.mockaroo.com/marketplace?key=63304c70";

    private List<Marketplace> downloadAllMarketplace() throws ParseException {
        String data = ApiReader.getDataFromApi(MARKETPLACE_API);
        List<Marketplace> result = new ArrayList<>();

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            Marketplace marketplace = Marketplace.builder()
                    .id(Integer.parseInt(String.valueOf(obj.get("id"))))
                    .marketplaceName(MarketplaceName.valueOf((String) obj.get("marketplace_name")))
                    .build();

            result.add(marketplace);
        }

        return result;
    }

    public boolean addMarketPlace() throws ParseException {
        if (isMarketplaceEmpty()) {
            List<Marketplace> marketplaces = downloadAllMarketplace();
            marketplaces.forEach(marketplace -> marketplaceDao.add(marketplace));
            marketplaceDao.addForeignKey();
            return true;
        }
        return false;
    }

    public void updateMarketPlace() throws ParseException {
        if (isMarketplaceEmpty()) {
            addMarketPlace();
        }
        else {
            List<Marketplace> marketplaces = downloadAllMarketplace();
            marketplaces.forEach(marketplace -> marketplaceDao.update(marketplace));
        }
    }

    public boolean removeAllMarketplace() throws ParseException {
        if (!isMarketplaceEmpty()) {
            marketplaceDao.removeAll();
            addMarketPlace();
            return true;
        }
        return false;
    }

    public boolean isMarketplaceEmpty() { return marketplaceDao.isEmpty(); }
}

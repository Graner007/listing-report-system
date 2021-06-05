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

@AllArgsConstructor
public class MarketplaceService {

    private final MarketplaceDao marketplaceDao;
    private static final String MARKETPLACE_API = "https://my.api.mockaroo.com/marketplace?key=63304c70";

    public void getMarketPlaces() throws ParseException {
        String data = ApiReader.getDataFromApi(MARKETPLACE_API);

        JSONParser parse = new JSONParser();
        JSONArray arr = (JSONArray) parse.parse(data);

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);

            Marketplace marketplace = Marketplace.builder()
                    .id((Long) obj.get("id"))
                    .marketplaceName(MarketplaceName.valueOf((String) obj.get("marketplace_name")))
                    .build();

            marketplaceDao.add(marketplace);
        }
    }
}

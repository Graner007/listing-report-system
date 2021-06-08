package com.codecool.report;

import com.codecool.report.config.DatabaseConnection;
import com.codecool.report.config.PropertyReader;
import com.codecool.report.dao.LocationDao;
import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.dao.PlantDao;
import com.codecool.report.dao.StatusDao;
import com.codecool.report.dao.jdbc.LocationDaoJdbc;
import com.codecool.report.dao.jdbc.MarketplaceDaoJdbc;
import com.codecool.report.dao.jdbc.PlantDaoJdbc;
import com.codecool.report.dao.jdbc.StatusDaoJdbc;
import com.codecool.report.service.LocationService;
import com.codecool.report.service.MarketplaceService;
import com.codecool.report.service.PlantService;
import com.codecool.report.service.StatusService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ParseException, IOException {
        PropertyReader propertyReader = new PropertyReader();
        String databaseName = propertyReader.getDatabaseName();
        String username = propertyReader.getUsername();
        String password = propertyReader.getPassword();

        DatabaseConnection db = new DatabaseConnection(databaseName, username, password);

        Connection conn = db.connect();

        PlantDao plantDao = new PlantDaoJdbc(conn);
        LocationDao locationDao = new LocationDaoJdbc(conn);
        StatusDao statusDao = new StatusDaoJdbc(conn);
        MarketplaceDao marketplaceDao = new MarketplaceDaoJdbc(conn);

        StatusService statusService = new StatusService(statusDao);
        statusService.getAllStatus();

        MarketplaceService marketplaceService = new MarketplaceService(marketplaceDao);
        marketplaceService.getMarketPlace();

        LocationService locationService = new LocationService(locationDao);
        locationService.getAllLocation();

        PlantService service = new PlantService(plantDao, locationDao, marketplaceDao, statusDao);
        service.getAllPlant();
    }
}

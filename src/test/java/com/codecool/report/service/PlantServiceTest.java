package com.codecool.report.service;

import com.codecool.report.dao.LocationDao;
import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.dao.PlantDao;
import com.codecool.report.dao.StatusDao;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlantServiceTest {

    private PlantService plantService;

    @BeforeAll
    void setup() {
        LocationDao mockedLocationDao = mock(LocationDao.class);
        MarketplaceDao mockedMarketplaceDao = mock(MarketplaceDao.class);
        StatusDao mockedStatusDao = mock(StatusDao.class);
        PlantDao mockedPlantDao = mock(PlantDao.class);
        plantService = new PlantService(mockedPlantDao, mockedLocationDao, mockedMarketplaceDao, mockedStatusDao);
    }



}
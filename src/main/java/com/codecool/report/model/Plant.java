package com.codecool.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Plant {

    private UUID id;

    private String title;

    private String description;

    private UUID locationId;

    private double listingPrice;

    private String currency;

    private int quantity;

    private int statusId;

    private int marketplaceId;

    private Date uploadTime;

    private String ownerEmailAddress;
}

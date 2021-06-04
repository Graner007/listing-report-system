package com.codecool.report.model;

import com.codecool.report.model.marketplace.Marketplace;
import com.codecool.report.model.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    private Location location;

    private int listingPrice;

    private double currency;

    private int quantity;

    private Status status;

    private Marketplace marketplace;

    private LocalDate uploadTime;

    private String ownerEmailAddress;
}

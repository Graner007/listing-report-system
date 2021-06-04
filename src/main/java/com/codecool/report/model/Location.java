package com.codecool.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Location {

    @Id
    private UUID id;

    private String managerName;

    private String phone;

    private String addressPrimary;

    private String addressSecondary;

    private String country;

    private String town;

    private String postalCode;
}

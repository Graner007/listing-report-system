package com.codecool.report.model.status;

public enum StatusName {

    ACTIVE("active"),
    SCHEDULED("scheduled"),
    CANCELLED("cancelled");

    private final String status;

    StatusName(String status) { this.status = status; }

    public String getStatus() { return status; }
}

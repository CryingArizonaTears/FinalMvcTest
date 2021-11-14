package com.senla.model;

public enum AdStatus {
    OPEN("OPEN"),
    CLOSED("CLOSED");

    AdStatus(String name) {
        this.name = name;
    }

    private String name;
}

package com.senla.model;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    Role(String name) {
        this.name = name;
    }

    private final String name;
}

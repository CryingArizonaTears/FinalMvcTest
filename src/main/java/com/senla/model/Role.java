package com.senla.model;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    Role(String name) {
        this.name = name;
    }

    private final String name;
}

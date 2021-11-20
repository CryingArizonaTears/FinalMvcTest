package com.senla.model.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceFilter {

    private String name;
    private String description;
    private Double price;
    private Integer plusDays;
}

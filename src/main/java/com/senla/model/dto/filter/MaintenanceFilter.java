package com.senla.model.dto.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MaintenanceFilter {

    private String name;
    private String description;
    private Double priceFrom;
    private Double priceTo;
    private Integer plusDays;
}

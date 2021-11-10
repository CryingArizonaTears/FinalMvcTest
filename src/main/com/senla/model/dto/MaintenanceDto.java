package com.senla.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer plusDays;
}

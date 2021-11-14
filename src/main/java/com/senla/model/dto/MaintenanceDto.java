package com.senla.model.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MaintenanceDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer plusDays;
}

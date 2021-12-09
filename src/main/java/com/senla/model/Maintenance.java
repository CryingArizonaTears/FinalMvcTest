package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@Entity
@Table(name = "maintenance")
public class Maintenance extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "maintenanceName", nullable = false)
    private String name;
    @Column(name = "maintenanceDescription", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "plusDays", nullable = false)
    private Integer plusDays;
}

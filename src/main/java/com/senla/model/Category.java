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
@Table(name = "category")
public class Category extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "categoryName", nullable = false)
    private String name;
}

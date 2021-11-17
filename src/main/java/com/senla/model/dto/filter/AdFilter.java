package com.senla.model.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdFilter {
    private Long id;
    private String name;
    private String categoryName;
    private Long userId;
    private String status;
    private Double priceFrom;
    private Double priceTo;
    private String orderBy;
    private String orderDirection;
}

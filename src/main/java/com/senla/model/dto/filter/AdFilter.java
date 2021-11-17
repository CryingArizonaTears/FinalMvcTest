package com.senla.model.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdFilter {
    private String name;
    private String categoryName;
    private Long userId;
    private Double priceFrom;
    private Double priceTo;
    private String orderBy;
    private String orderDirection;
}

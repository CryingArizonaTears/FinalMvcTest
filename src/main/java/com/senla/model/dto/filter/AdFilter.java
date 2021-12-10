package com.senla.model.dto.filter;

import com.senla.model.AdStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AdFilter {
    private Long id;
    private String name;
    private String categoryName;
    private Long userId;
    private AdStatus status;
    private Double priceFrom;
    private Double priceTo;
    private String orderBy;
    private String orderDirection;
}

package com.senla.model.dto.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingFilter {

    private Long sender;
    private Long receiver;
}

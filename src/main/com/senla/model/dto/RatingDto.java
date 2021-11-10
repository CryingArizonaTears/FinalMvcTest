package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RatingDto {

    private Long id;
    private Integer rating;
    private UserProfileDto sender;
    private UserProfileDto receiver;
    private LocalDate creationDate;
}

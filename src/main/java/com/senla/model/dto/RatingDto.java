package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RatingDto {

    private Long id;
    private Integer rating;
    private UserProfileDto sender;
    private UserProfileDto receiver;
    private LocalDate creationDate;
}

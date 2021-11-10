package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {

    private Long id;
    private String fullName;
    private String role;
    private ChatDto chats;
    private AdDto ads;
    private RatingDto ratings;
    private Double avgRating;
}

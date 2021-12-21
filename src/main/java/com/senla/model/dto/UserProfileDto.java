package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString
public class UserProfileDto {

    private Long id;
    private String fullName;
    private Role role;
    @JsonIgnore
    @ToString.Exclude
    private List<ChatDto> chats;
    @JsonIgnore
    @ToString.Exclude
    private List<AdDto> ads;
    @JsonIgnore
    @ToString.Exclude
    private List<RatingDto> ratings;
    private Double avgRating;
    @JsonIgnore
    @ToString.Exclude
    private UserCredentialsDto userLogin;
}

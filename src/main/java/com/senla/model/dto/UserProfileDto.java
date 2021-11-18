package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class UserProfileDto {

    private Long id;
    private String fullName;
    private Role role;
    @JsonIgnore
    private List<ChatDto> chats;
    @JsonIgnore
    private List<AdDto> ads;
    @JsonIgnore
    private List<RatingDto> ratings;
    private Double avgRating;
    @JsonIgnore
    private UserLoginDto userLogin;

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", avgRating=" + avgRating +
                '}';
    }
}

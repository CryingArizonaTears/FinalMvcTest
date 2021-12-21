package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;
    @JsonIgnore
    @ToString.Exclude
    private AdDto ad;
    private UserProfileDto userProfile;
    private String text;
    private LocalDate creationDate;
}

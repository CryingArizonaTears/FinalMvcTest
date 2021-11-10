package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private AdDto ad;
    private UserProfileDto userProfile;
    private String text;
    private LocalDate creationDate;
}

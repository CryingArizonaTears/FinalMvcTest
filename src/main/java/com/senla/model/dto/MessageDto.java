package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MessageDto {

    private Long id;
    @JsonBackReference
    private ChatDto chat;
    private UserProfileDto sender;
    private String text;
    private LocalDate date;
}

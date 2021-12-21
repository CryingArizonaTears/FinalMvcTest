package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ChatDto {

    private Long id;
    private String name;
    private List<UserProfileDto> users;
    @JsonManagedReference
    private List<MessageDto> messages;
}

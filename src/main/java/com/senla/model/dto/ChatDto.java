package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDto {

    private Long id;
    private String name;
    private List<UserProfileDto> users;
    private List<MessageDto> messages;

    @Override
    public String toString() {
        return "ChatDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}

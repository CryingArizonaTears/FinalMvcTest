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
    //    private UserProfileDto firstUser;
//    private UserProfileDto secondUser;
    private List<MessageDto> messages;

    @Override
    public String toString() {
        return "ChatDto{" +
                "id=" + id +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}

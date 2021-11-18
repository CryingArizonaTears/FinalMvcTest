package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MessageDto {

    private Long id;
    @JsonIgnore
    private ChatDto chat;
    private UserProfileDto sender;
    private String text;
    private LocalDate date;

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", chat=" + chat.getId() +
                ", sender=" + sender.getId() +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}

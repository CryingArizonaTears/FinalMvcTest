package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MessageDto {

    private Long id;
    @JsonBackReference
    private ChatDto chat;
    private UserProfileDto sender;
    private String text;
    private LocalDate date;

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", chat=" + chat +
                ", sender=" + sender +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}

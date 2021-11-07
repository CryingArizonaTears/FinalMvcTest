package com.senla.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatId", nullable = false)
    private Chat chat;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userSenderId", nullable = false)
    private UserProfile sender;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender.getId() +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}

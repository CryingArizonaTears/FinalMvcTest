package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "message")
public class Message extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatId", nullable = false)
    @ToString.Exclude
    private Chat chat;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userSenderId", nullable = false)
    private UserProfile sender;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;
}

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
@Table(name = "rating")
public class Rating extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userSenderId", nullable = false)
    private UserProfile sender;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "userReceiverId", nullable = false)
    private UserProfile receiver;
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;
}

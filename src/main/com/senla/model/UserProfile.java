package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "userProfile")
public class UserProfile extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fullName", nullable = false)
    private String fullName;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "FirstUser")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<Chat> createdChats;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "SecondUser")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<Chat> receivedChats;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "usersChats",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "chatId")
    )
    private List<Chat> chats;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ad> ads;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rating> ratings;
    @Column(name = "avgRating", nullable = false)
    private Double avgRating;
}

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
@Table(name = "chat")
public class Chat extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "usersChats",
            joinColumns = @JoinColumn(name = "chatId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<UserProfile> users;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messages;
}

package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firstUserId", nullable = false)
    private UserProfile firstUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondUserId", nullable = false)
    private UserProfile secondUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messages;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", FirstUser=" + firstUser.getId() +
                ", SecondUser=" + secondUser.getId() +
                ", messages=" + messages +
                '}';
    }
}

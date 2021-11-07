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
    private UserProfile FirstUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondUserId", nullable = false)
    private UserProfile SecondUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messages;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", FirstUser=" + FirstUser.getId() +
                ", SecondUser=" + SecondUser.getId() +
                ", messages=" + messages +
                '}';
    }
}

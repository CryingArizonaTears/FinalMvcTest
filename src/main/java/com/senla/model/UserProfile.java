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
    @ManyToMany(mappedBy = "users")
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<Chat> chats;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile")
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<Ad> ads;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    private List<Rating> ratings;
    @Column(name = "avgRating", nullable = false)
    private Double avgRating;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userLoginId", nullable = false)
    @ToString.Exclude
    private UserLogin userLogin;
}

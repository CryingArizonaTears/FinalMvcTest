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
@Table(name = "userProfile")
public class UserProfile extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fullName", nullable = false)
    private String fullName;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "users",cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Chat> chats;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "userProfile")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ad> ads;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "receiver")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Rating> ratings;
    @Column(name = "avgRating", nullable = false)
    private Double avgRating;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userLoginId", nullable = false)
    private UserLogin userLogin;

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", avgRating=" + avgRating +
                '}';
    }
}

package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "userLogin")
public class UserLogin extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfileId", nullable = false)
    private UserProfile userProfile;
}

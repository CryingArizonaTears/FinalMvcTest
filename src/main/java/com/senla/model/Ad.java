package com.senla.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ad")
public class Ad extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "adName", nullable = false)
    private String name;
    @Column(name = "adStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdStatus status;
    @Column(name = "premiumUntilDate")
    private LocalDate premiumUntilDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    @Column(name = "adDescription", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private UserProfile userProfile;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments;
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "adsMaintenances",
            joinColumns = @JoinColumn(name = "adId"),
            inverseJoinColumns = @JoinColumn(name = "maintenanceId")
    )
    private List<Maintenance> maintenances;
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;
}

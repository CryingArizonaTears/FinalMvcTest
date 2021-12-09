package com.senla.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends AbstractModel {

    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "adId ", nullable = false)
    private Ad ad;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", nullable = false)
    private UserProfile userProfile;
    @Column(name = "commentText", nullable = false)
    private String text;
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userProfile=" + userProfile.getId() +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}

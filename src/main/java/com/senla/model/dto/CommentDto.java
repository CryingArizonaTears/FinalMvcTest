package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDto {

    private Long id;
    @JsonIgnore
    private AdDto ad;
    private UserProfileDto userProfile;
    private String text;
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", ad=" + ad.getId() +
                ", userProfile=" + userProfile.getId() +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}

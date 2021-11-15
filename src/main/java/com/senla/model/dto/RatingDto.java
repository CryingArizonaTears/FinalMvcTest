package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RatingDto {

    private Long id;
    private Integer rating;
    private UserProfileDto sender;
    private UserProfileDto receiver;
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "RatingDto{" +
                "id=" + id +
                ", rating=" + rating +
                ", sender=" + sender.getId() +
                ", receiver=" + receiver.getId() +
                ", creationDate=" + creationDate +
                '}';
    }
}
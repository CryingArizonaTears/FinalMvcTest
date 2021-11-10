package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdDto {

    private Long id;
    private String name;
    private String adStatus;
    private LocalDate premiumUntilDate;
    private CategoryDto category;
    private String description;
    private Double price;
    private UserProfileDto userProfile;
    private CommentDto comments;
    private MaintenanceDto maintenances;
    private LocalDate creationDate;
}

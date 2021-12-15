package com.senla.model.dto;

import com.senla.model.AdStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AdDto {

    private Long id;
    private String name;
    private AdStatus adStatus;
    private LocalDate premiumUntilDate;
    private CategoryDto category;
    private String description;
    private Double price;
    private UserProfileDto userProfile;
    private List<CommentDto> comments;
    private List<MaintenanceDto> maintenances;
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "AdDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adStatus=" + adStatus +
                ", premiumUntilDate=" + premiumUntilDate +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", userProfile=" + userProfile +
                ", comments=" + comments +
                ", maintenances=" + maintenances +
                ", creationDate=" + creationDate +
                '}';
    }
}

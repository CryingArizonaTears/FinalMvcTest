package com.senla.api.service;

import com.senla.model.dto.RatingDto;

public interface IRatingService {

    void addMarkToUser(RatingDto ratingDto);
}

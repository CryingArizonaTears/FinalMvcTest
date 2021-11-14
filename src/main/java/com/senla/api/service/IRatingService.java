package com.senla.api.service;

public interface IRatingService {

    void addMarkToUser(Long receiverId, Long senderId, Integer mark);
}

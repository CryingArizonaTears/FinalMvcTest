package com.senla.api.service;

public interface ICommentService {

    void createComment(Long userId, Long adId, String text);
}

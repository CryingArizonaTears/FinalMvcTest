package com.senla.api.service;

import com.senla.model.dto.CommentDto;

public interface ICommentService {

    void createComment(CommentDto commentDto);
}

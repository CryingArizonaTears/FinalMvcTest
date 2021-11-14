package com.senla.api.dao;

import com.senla.model.Comment;

import java.util.List;

public interface ICommentDao {

    List<Comment> getAll();

    Comment get(Long id);

    Comment update(Comment comment);

    void save(Comment comment);

    void delete(Long id);
}

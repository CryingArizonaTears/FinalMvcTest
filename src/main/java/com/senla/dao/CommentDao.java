package com.senla.dao;

import com.senla.api.dao.ICommentDao;
import com.senla.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao extends AbstractDao<Comment> implements ICommentDao {

    @Override
    protected Class<Comment> getClazz() {
        return Comment.class;
    }
}

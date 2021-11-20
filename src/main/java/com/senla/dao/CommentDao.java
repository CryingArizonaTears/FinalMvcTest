package com.senla.dao;

import com.senla.api.dao.ICommentDao;
import com.senla.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class CommentDao extends AbstractDao<Comment> implements ICommentDao {

    @Override
    protected Class<Comment> getClazz() {
        return Comment.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
        return null;
    }
}

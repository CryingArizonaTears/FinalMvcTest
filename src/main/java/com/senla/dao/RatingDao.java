package com.senla.dao;

import com.senla.api.dao.IRatingDao;
import com.senla.model.Rating;
import com.senla.model.dto.filter.RatingFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class RatingDao extends AbstractDao<Rating> implements IRatingDao {

    @Override
    protected Class<Rating> getClazz() {
        return Rating.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
        return ratingPredicate((RatingFilter) object, criteriaBuilder, root);
    }


    private Predicate[] ratingPredicate(RatingFilter ratingFilter, CriteriaBuilder builder, Root<Rating> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (ratingFilter.getReceiver() != null) {
            predicates.add(builder.equal(root.get("receiver"), ratingFilter.getReceiver()));
        }
        if (ratingFilter.getSender() != null) {
            predicates.add(builder.equal(root.get("sender"), ratingFilter.getSender()));
        }
        return predicates.toArray(new Predicate[]{});
    }
}

package com.senla.dao;

import com.senla.api.dao.IRatingDao;
import com.senla.model.Rating;
import com.senla.model.Rating_;
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
public class RatingDao extends AbstractFilterDao<Rating, RatingFilter> implements IRatingDao {

    @Override
    protected Class<Rating> getClazz() {
        return Rating.class;
    }

    @Override
    protected Predicate[] getPredicates(RatingFilter ratingFilter, CriteriaBuilder builder, Root<Rating> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (ratingFilter.getReceiver() != null) {
            predicates.add(builder.equal(root.get(Rating_.RECEIVER), ratingFilter.getReceiver()));
        }
        if (ratingFilter.getSender() != null) {
            predicates.add(builder.equal(root.get(Rating_.SENDER), ratingFilter.getSender()));
        }
        return predicates.toArray(new Predicate[]{});
    }
}

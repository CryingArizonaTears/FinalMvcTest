package com.senla.dao;

import com.senla.api.dao.IRatingDao;
import com.senla.model.Rating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class RatingDao extends AbstractDao<Rating> implements IRatingDao {

    @Override
    protected Class<Rating> getClazz() {
        return Rating.class;
    }

    public List<Rating> filterByUserId(Long id) {

        Query query = getCurrentSession().createQuery("from Rating r where r.receiver = '" + id + "'");
        return (List<Rating>) query.getResultList();
    }
}

package com.senla.dao;

import com.senla.api.dao.IAdDao;
import com.senla.model.Ad;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public class AdDao extends AbstractDao<Ad> implements IAdDao {

    @Override
    protected Class<Ad> getClazz() {
        return Ad.class;
    }


    @Override
    public List getCurrentAds() {

        Session session = getCurrentSession();
        Query query1 = session.createQuery("select a from Ad a join a.userProfile u where a.status = 'OPEN' and a.premiumUntilDate >= '" + LocalDate.now() + "' order by avgRating desc");
        Query query2 = session.createQuery("select a from Ad a join a.userProfile u where a.status = 'OPEN' and (a.premiumUntilDate < '" + LocalDate.now() + "' or a.premiumUntilDate = null) order by avgRating desc");
        List list1 = query1.getResultList();
        List list2 = query2.getResultList();
        list1.addAll(list2);
        return list1;
    }

    public List filterClosedAdsByUserId(Long id) {
        Query query = getCurrentSession().createQuery("from Ad a where a.category = '" + id + "' AND a.status = 'CLOSED'");
        return query.getResultList();
    }


    @Override
    public List<Ad> getByName(String name) {

        Query query = getCurrentSession().createQuery("from Ad a where a.name = '" + name + "' AND a.status = 'OPEN'");
        return (List<Ad>) query.getResultList();
    }

    @Override
    public List<Ad> filterByCategory(Long id) {

        Query query = getCurrentSession().createQuery("from Ad a where a.category = '" + id + "' AND a.status = 'OPEN'");
        return (List<Ad>) query.getResultList();
    }

    @Override
    public List<Ad> filterByUserId(Long id) {

        Query query = getCurrentSession().createQuery("from Ad a where a.userProfile = '" + id + "' AND a.status = 'OPEN'");
        return (List<Ad>) query.getResultList();
    }

    @Override
    public List<Ad> filterByPrice(Double from, Double to) {

        Query query = getCurrentSession().createQuery("from Ad a where a.price >= " + from + "AND a.price <=" + to + "AND a.status = 'OPEN'");
        return (List<Ad>) query.getResultList();
    }

}

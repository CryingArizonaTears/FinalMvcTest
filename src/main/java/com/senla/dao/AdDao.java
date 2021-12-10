package com.senla.dao;

import com.senla.api.dao.IAdDao;
import com.senla.model.Ad;
import com.senla.model.Ad_;
import com.senla.model.Category_;
import com.senla.model.UserProfile_;
import com.senla.model.dto.filter.AdFilter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class AdDao extends AbstractFilterDao<Ad, AdFilter> implements IAdDao {

    @Override
    protected Class<Ad> getClazz() {
        return Ad.class;
    }

    @Override
    protected Predicate[] getPredicates(AdFilter object, CriteriaBuilder criteriaBuilder, Root<Ad> root) {
        return null;
    }

    @Override
    public List<Ad> getByFilter(AdFilter adFilter) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Ad> query = builder.createQuery(Ad.class);
        Root<Ad> root = query.from(Ad.class);
        if (adsPredicate(adFilter, builder, root).length != 0) {
            query.where(adsPredicate(adFilter, builder, root));
            if (adFilter.getOrderBy() != null && adFilter.getOrderDirection() != null) {
                if (adFilter.getOrderDirection().equals("asc")) {
                    query.orderBy(builder.asc(root.get(adFilter.getOrderBy())));
                }
                if (adFilter.getOrderDirection().equals("desc")) {
                    query.orderBy(builder.desc(root.get(adFilter.getOrderBy())));
                }
            }
            CriteriaQuery<Ad> all = query.select(root);
            return getCurrentSession().createQuery(all).getResultList();
        } else {
            Session session = getCurrentSession();
            Query query1 = session.createQuery("select a from Ad a join a.userProfile u where a.status = 'OPEN' and a.premiumUntilDate >= '" + LocalDate.now() + "' order by avgRating desc");
            Query query2 = session.createQuery("select a from Ad a join a.userProfile u where a.status = 'OPEN' and (a.premiumUntilDate < '" + LocalDate.now() + "' or a.premiumUntilDate = null) order by avgRating desc");
            List<Ad> list1 = query1.getResultList();
            List<Ad> list2 = query2.getResultList();
            list1.addAll(list2);
            return list1;
        }
    }

    private Predicate[] adsPredicate(AdFilter adFilter, CriteriaBuilder builder, Root<Ad> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(adFilter.getName())) {
            predicates.add(builder.like(root.get(Ad_.NAME), "%" + adFilter.getName() + "%"));
        }
        if (!ObjectUtils.isEmpty(adFilter.getCategoryName())) {
            predicates.add(builder.like(root.join(Ad_.CATEGORY).get(Category_.NAME), "%" + adFilter.getCategoryName() + "%"));
        }
        if (adFilter.getUserId() != null) {
            predicates.add(builder.equal(root.join(Ad_.USER_PROFILE).get(UserProfile_.ID), adFilter.getUserId()));
        }
        if (adFilter.getPriceFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Ad_.PRICE), adFilter.getPriceFrom()));
        }
        if (adFilter.getPriceTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Ad_.PRICE), adFilter.getPriceTo()));
        }
        if (adFilter.getStatus() != null) {
            predicates.add(builder.equal(root.get(Ad_.STATUS), adFilter.getStatus()));
        }
        if (adFilter.getId() != null) {
            predicates.add(builder.equal(root.get(Ad_.ID), adFilter.getId()));
        }
        return predicates.toArray(new Predicate[]{});
    }

}

package com.senla.dao;

import com.senla.api.dao.IAdDao;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
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
public class AdDao extends AbstractDao<Ad> implements IAdDao {

    @Override
    protected Class<Ad> getClazz() {
        return Ad.class;
    }

    @Override
    protected Predicate[] getPredicates(Object object, CriteriaBuilder criteriaBuilder, Root root) {
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
//            query.where(currentAdsPredicateFirst(builder, root));
//            query.orderBy(builder.desc(root.join("userProfile").get("avgRating")));
//            CriteriaQuery<Ad> all1 = query.select(root);
//            List<Ad> list1 = getCurrentSession().createQuery(all1).getResultList();
//
//            CriteriaBuilder builder2 = getCurrentSession().getCriteriaBuilder();
//            CriteriaQuery<Ad> query2 = builder2.createQuery(Ad.class);
//            Root<Ad> root2 = query2.from(Ad.class);
//            query2.where(currentAdsPredicateSecond(builder2, root2));
//            query2.orderBy(builder2.desc(root2.join("userProfile").get("avgRating")));
//            CriteriaQuery<Ad> all2 = query2.select(root2);
//            List<Ad> list2 = getCurrentSession().createQuery(all2).getResultList();
//
//            list1.addAll(list2);
//            return list1;
            Session session = getCurrentSession();
            Query query1 = session.createQuery("select a from Ad a join a.userProfile u where a.status = 'OPEN' and a.premiumUntilDate >= '" + LocalDate.now() + "' order by avgRating desc");
            Query query2 = session.createQuery("select a from Ad a join a.userProfile u where a.status = 'OPEN' and (a.premiumUntilDate < '" + LocalDate.now() + "' or a.premiumUntilDate = null) order by avgRating desc");
            List list1 = query1.getResultList();
            List list2 = query2.getResultList();
            list1.addAll(list2);
            return list1;
        }
    }

    private Predicate[] adsPredicate(AdFilter adFilter, CriteriaBuilder builder, Root<Ad> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(adFilter.getName())) {
            predicates.add(builder.like(root.get("name"), "%" + adFilter.getName() + "%"));
        }
        if (!ObjectUtils.isEmpty(adFilter.getCategoryName())) {
            predicates.add(builder.like(root.join("category").get("name"), "%" + adFilter.getCategoryName() + "%"));
        }
        if (adFilter.getUserId() != null) {
            predicates.add(builder.equal(root.join("userProfile").get("id"), adFilter.getUserId()));
        }
        if (adFilter.getPriceFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), adFilter.getPriceFrom()));
        }
        if (adFilter.getPriceTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("price"), adFilter.getPriceTo()));
        }
        if (adFilter.getStatus() != null) {
            predicates.add(builder.equal(root.get("status"), adFilter.getStatus()));
        }
        if (adFilter.getId() != null) {
            predicates.add(builder.equal(root.get("id"), adFilter.getId()));
        }
        return predicates.toArray(new Predicate[]{});
    }

    private Predicate[] currentAdsPredicateFirst(CriteriaBuilder builder, Root<Ad> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("status"), AdStatus.OPEN));
        predicates.add(builder.greaterThanOrEqualTo(root.get("premiumUntilDate"), LocalDate.now()));
        return predicates.toArray(new Predicate[]{});
    }


    private Predicate[] currentAdsPredicateSecond(CriteriaBuilder builder, Root<Ad> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("status"), AdStatus.OPEN));
        predicates.add(builder.lessThan(root.get("premiumUntilDate"), LocalDate.now()));
        predicates.add(builder.equal(root.get("premiumUntilDate"), null));
        return predicates.toArray(new Predicate[]{});
    }

}

package com.senla.dao;

import com.senla.api.dao.IAdDao;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
import com.senla.model.dto.filter.AdFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public List<Ad> getByFilter(AdFilter adFilter) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Ad> query = builder.createQuery(Ad.class);
        Root<Ad> root = query.from(Ad.class);
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
    }


    @Override
    public List<Ad> filterClosedAdsByUserId(Long id) {
//        Query query = getCurrentSession().createQuery("from Ad a where a.category = '" + id + "' AND a.status = 'CLOSED'");
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Ad> query = builder.createQuery(Ad.class);
        Root<Ad> root = query.from(Ad.class);
        query.where(adsClosedPredicate(id, builder, root));
        CriteriaQuery<Ad> all = query.select(root);
        return getCurrentSession().createQuery(all).getResultList();
//        return (List<Ad>) query.getResultList();
    }

    private Predicate[] adsClosedPredicate(Long id, CriteriaBuilder builder, Root<Ad> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.join("userProfile").get("id"), id));
        predicates.add(builder.equal(root.get("status"), AdStatus.CLOSED));
        return predicates.toArray(new Predicate[]{});
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
        return predicates.toArray(new Predicate[]{});
    }

}

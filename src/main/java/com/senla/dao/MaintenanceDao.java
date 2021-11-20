package com.senla.dao;

import com.senla.api.dao.IMaintenanceDao;
import com.senla.model.Category;
import com.senla.model.Maintenance;
import com.senla.model.dto.filter.CategoryFilter;
import com.senla.model.dto.filter.MaintenanceFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MaintenanceDao extends AbstractDao<Maintenance> implements IMaintenanceDao {

    @Override
    protected Class<Maintenance> getClazz() {
        return Maintenance.class;
    }

    @Override
    protected Method getMethod() {
        try {
            return MaintenanceDao.class.getMethod("maintenancePredicate", MaintenanceFilter.class, CriteriaBuilder.class, Root.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<Maintenance> getByFilter(MaintenanceFilter maintenanceFilter) {
//        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
//        CriteriaQuery<Maintenance> query = builder.createQuery(Maintenance.class);
//        Root<Maintenance> root = query.from(Maintenance.class);
//        query.where(maintenancePredicate(maintenanceFilter, builder, root));
//        CriteriaQuery<Maintenance> all = query.select(root);
//        return getCurrentSession().createQuery(all).getResultList();
//    }

    private Predicate[] maintenancePredicate(MaintenanceFilter maintenanceFilter, CriteriaBuilder builder, Root<Maintenance> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(maintenanceFilter.getName())) {
            predicates.add(builder.like(root.get("name"), "%" + maintenanceFilter.getName() + "%"));
        }
        if (!ObjectUtils.isEmpty(maintenanceFilter.getDescription())) {
            predicates.add(builder.like(root.get("description"), "%" + maintenanceFilter.getDescription() + "%"));
        }
        if (maintenanceFilter.getPrice() != null) {
            predicates.add(builder.equal(root.get("price"), maintenanceFilter.getPrice()));
        }
        if (maintenanceFilter.getPlusDays() != null) {
            predicates.add(builder.equal(root.get("plusDays"), maintenanceFilter.getPlusDays()));
        }
        return predicates.toArray(new Predicate[]{});
    }
}

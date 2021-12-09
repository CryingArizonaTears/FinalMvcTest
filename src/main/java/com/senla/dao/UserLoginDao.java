package com.senla.dao;

import com.senla.api.dao.IUserLoginDao;
import com.senla.model.UserLogin;
import com.senla.model.UserLogin_;
import com.senla.model.dto.filter.UserFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserLoginDao extends AbstractFilterDao<UserLogin, UserFilter> implements IUserLoginDao {

    @Override
    protected Class<UserLogin> getClazz() {
        return UserLogin.class;
    }

    @Override
    protected Predicate[] getPredicates(UserFilter userFilter, CriteriaBuilder builder, Root<UserLogin> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(userFilter.getUsername())) {
            predicates.add(builder.equal(root.get(UserLogin_.USERNAME), userFilter.getUsername()));
        }
        return predicates.toArray(new Predicate[]{});
    }
}

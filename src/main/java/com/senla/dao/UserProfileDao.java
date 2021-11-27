package com.senla.dao;

import com.senla.api.dao.IUserProfileDao;
import com.senla.model.UserProfile;
import com.senla.model.dto.filter.UserFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserProfileDao extends AbstractFilterDao<UserProfile, UserFilter> implements IUserProfileDao {

    @Override
    protected Class<UserProfile> getClazz() {
        return UserProfile.class;
    }

    @Override
    protected Predicate[] getPredicates(UserFilter userFilter, CriteriaBuilder builder, Root<UserProfile> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (userFilter.getId() != null) {
            predicates.add(builder.equal(root.get("id"), userFilter.getId()));
        }
        if (!ObjectUtils.isEmpty(userFilter.getUsername())) {
            predicates.add(builder.equal(root.join("userLogin").get("username"), userFilter.getUsername()));
        }
        return predicates.toArray(new Predicate[]{});
    }

}

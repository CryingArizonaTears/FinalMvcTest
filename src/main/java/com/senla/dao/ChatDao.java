package com.senla.dao;

import com.senla.api.dao.IChatDao;
import com.senla.model.Ad;
import com.senla.model.AdStatus;
import com.senla.model.Chat;
import com.senla.model.dto.filter.AdFilter;
import com.senla.model.dto.filter.ChatFilter;
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
public class ChatDao extends AbstractDao<Chat> implements IChatDao {

    @Override
    protected Class<Chat> getClazz() {
        return Chat.class;
    }

    @Override
    protected Method getMethod() {
        try {
            return ChatDao.class.getMethod("chatsPredicate", ChatFilter.class, CriteriaBuilder.class, Root.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


//    @Override
//    public List<Chat> getByFilter(ChatFilter chatFilter) {
//        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
//        CriteriaQuery<Chat> query = builder.createQuery(Chat.class);
//        Root<Chat> root = query.from(Chat.class);
//        query.where(chatsPredicate(chatFilter, builder, root));
//        CriteriaQuery<Chat> all = query.select(root);
//        return getCurrentSession().createQuery(all).getResultList();
//    }

    private Predicate[] chatsPredicate(ChatFilter chatFilter, CriteriaBuilder builder, Root<Chat> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (chatFilter.getId() != null) {
            predicates.add(builder.equal(root.get("id"), chatFilter.getId()));
        }
        if (!ObjectUtils.isEmpty(chatFilter.getName())) {
            predicates.add(builder.like(root.get("name"), "%" + chatFilter.getName() + "%"));
        }
        if (!ObjectUtils.isEmpty(chatFilter.getText())) {
            predicates.add(builder.like(root.join("messages").get("text"), "%" + chatFilter.getText() + "%"));
        }
        if (chatFilter.getUserId() != null) {
            predicates.add(builder.equal(root.join("users").get("id"), chatFilter.getUserId()));
        }
        return predicates.toArray(new Predicate[]{});
    }

}

package com.senla.dao;

import com.senla.api.dao.IChatDao;
import com.senla.model.Chat;
import com.senla.model.dto.filter.ChatFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatDao extends AbstractFilterDao<Chat, ChatFilter> implements IChatDao {

    @Override
    protected Class<Chat> getClazz() {
        return Chat.class;
    }

    @Override
    protected Predicate[] getPredicates(ChatFilter chatFilter, CriteriaBuilder builder, Root<Chat> root) {

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

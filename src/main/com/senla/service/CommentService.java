package com.senla.service;

import com.senla.api.dao.ICommentDao;
import com.senla.api.service.ICommentService;
import com.senla.model.Ad;
import com.senla.model.Comment;
import com.senla.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentDao commentDao;

    @Override
    public void createComment(Long userId, Long adId, String text) {
        Comment comment = new Comment();
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        Ad ad = new Ad();
        ad.setId(adId);
        comment.setAd(ad);
        comment.setUserProfile(userProfile);
        comment.setText(text);
        comment.setCreationDate(LocalDate.now());
        commentDao.save(comment);

    }
}

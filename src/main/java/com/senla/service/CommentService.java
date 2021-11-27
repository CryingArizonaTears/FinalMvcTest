package com.senla.service;

import com.senla.api.dao.ICommentDao;
import com.senla.api.service.ICommentService;
import com.senla.model.Ad;
import com.senla.model.Comment;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.CommentDto;
import com.senla.modelMapperMethods.ModelMapperMapList;
import com.senla.security.AuthenticationGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private ModelMapperMapList modelMapper;
    @Autowired
    private AuthenticationGetUser authenticationGetUser;

    @Override
    public void createComment(Long id, CommentDto commentDto) {
        UserProfile userProfile = authenticationGetUser.getUserProfileByAuthentication();
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Ad ad = new Ad();
        ad.setId(id);
        comment.setAd(ad);
        comment.setCreationDate(LocalDate.now());
        if (userProfile.getRole().equals(Role.ROLE_USER)) {
            comment.setUserProfile(userProfile);
        }
        if (userProfile.getRole().equals(Role.ROLE_ADMIN)) {
            comment.setCreationDate(commentDto.getCreationDate());
        }
        commentDao.save(comment);
    }
}

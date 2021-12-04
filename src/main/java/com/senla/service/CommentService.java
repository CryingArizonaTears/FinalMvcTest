package com.senla.service;

import com.senla.api.dao.ICommentDao;
import com.senla.api.service.ICommentService;
import com.senla.api.service.IUserService;
import com.senla.model.Ad;
import com.senla.model.Comment;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.CommentDto;
import com.senla.modelMapperMethods.ExtendedModelMapper;
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
    private ExtendedModelMapper modelMapper;
    @Autowired
    private IUserService userService;

    @Override
    public void createComment(CommentDto commentDto) {
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        Comment comment = new Comment();
        Ad ad = new Ad();
        ad.setId(commentDto.getAd().getId());
        comment.setText(commentDto.getText());
        comment.setAd(ad);
        comment.setCreationDate(LocalDate.now());
        comment.setUserProfile(currentUser);
        if (currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            if (commentDto.getCreationDate() != null) {
                comment.setCreationDate(commentDto.getCreationDate());
            }
            if (commentDto.getUserProfile() != null) {
                comment.setUserProfile(modelMapper.map(commentDto.getUserProfile(), UserProfile.class));
            }
        }
        commentDao.save(comment);
    }
}

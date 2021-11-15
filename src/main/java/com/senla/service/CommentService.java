package com.senla.service;

import com.senla.api.dao.ICommentDao;
import com.senla.api.service.ICommentService;
import com.senla.model.Ad;
import com.senla.model.Comment;
import com.senla.model.UserProfile;
import com.senla.model.dto.CommentDto;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Override
    public void createComment(Long id, CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Ad ad = new Ad();
        ad.setId(id);
        comment.setAd(ad);
        comment.setCreationDate(LocalDate.now());
        commentDao.save(comment);

    }
}

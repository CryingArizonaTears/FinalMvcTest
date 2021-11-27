package com.senla.service;

import com.senla.api.dao.IRatingDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IRatingService;
import com.senla.model.Rating;
import com.senla.model.UserProfile;
import com.senla.model.dto.RatingDto;
import com.senla.model.dto.filter.RatingFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import com.senla.security.AuthenticationGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class RatingService implements IRatingService {
    @Autowired
    private IUserProfileDao userProfileDao;
    @Autowired
    private IRatingDao ratingDao;
    @Autowired
    private ModelMapperMapList modelMapper;
    @Autowired
    private AuthenticationGetUser authenticationGetUser;

    @Override
    public void addMarkToUser(RatingDto ratingDto) {
        UserProfile sender = authenticationGetUser.getUserProfileByAuthentication();
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        rating.setSender(sender);
        if (rating.getSender().equals(rating.getReceiver())) {
            try {
                throw new Exception("Вы не можете поставить себе оценку.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RatingFilter ratingFilter = new RatingFilter();
        ratingFilter.setReceiver(rating.getReceiver().getId());
        ratingFilter.setSender(rating.getSender().getId());
        if (ObjectUtils.isEmpty(ratingDao.getByFilter(ratingFilter))) {
            rating.setCreationDate(LocalDate.now());
            ratingDao.save(rating);
            RatingFilter ratingFilter2 = new RatingFilter();
            ratingFilter2.setReceiver(rating.getReceiver().getId());
            UserProfile receiver = userProfileDao.get(rating.getReceiver().getId());
            List<Rating> ratings = ratingDao.getByFilter(ratingFilter2);
            Integer sum = 0;
            for (Rating value : ratings) {
                sum += value.getRating();
            }
            Double avgRating = (double) (sum / ratings.size());
            receiver.setAvgRating(avgRating);
            userProfileDao.update(receiver);
        }
        if (!ObjectUtils.isEmpty(ratingDao.getByFilter(ratingFilter))) {
            try {
                throw new Exception("Пользователь уже получил от Вас оценку.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}


package com.senla.service;

import com.senla.api.dao.IRatingDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IRatingService;
import com.senla.model.Rating;
import com.senla.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class RatingService implements IRatingService {
    @Autowired
    private IUserProfileDao userProfileDao;
    @Autowired
    private IRatingDao ratingDao;

    @Override
    public void addMarkToUser(Long receiverId, Long senderId, Integer mark) {

        Rating rating = new Rating();
        rating.setRating(mark);
        rating.setCreationDate(LocalDate.now());
        UserProfile receiver = userProfileDao.get(receiverId);
        rating.setReceiver(receiver);
        UserProfile sender = new UserProfile();
        sender.setId(senderId);
        rating.setSender(sender);
        ratingDao.save(rating);
        List<Rating> ratings = ratingDao.filterByUserId(receiverId);
        Integer sum = 0;
        for (int i = 0; i < ratings.size(); i++) {
            sum += ratings.get(i).getRating();
        }
        Double avgRating = Double.valueOf(sum / ratings.size());
        receiver.setAvgRating(avgRating);
        userProfileDao.update(receiver);
    }
}

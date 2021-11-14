package com.senla.service;

import com.senla.api.dao.IChatDao;
import com.senla.api.dao.IMessageDao;
import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IChatAndMessageService;
import com.senla.model.Chat;
import com.senla.model.Message;
import com.senla.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Transactional
@Service
public class ChatAndMessageService implements IChatAndMessageService {
    @Autowired
    private IUserProfileDao userProfileDao;
    @Autowired
    private IChatDao chatDao;
    @Autowired
    private IMessageDao messageDao;


    @Override
    public void sendMessage(Long chatId, Long userId, String text) {

        Chat chat = new Chat();
        chat.setId(chatId);
        Message message = new Message();
        message.setChat(chat);
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        message.setSender(userProfile);
        message.setText(text);
        message.setCreationDate(LocalDate.now());
        messageDao.save(message);
    }

    @Override
    public Chat createChat(Long firstUser, Long secondUser) {

        Chat chat = new Chat();
        UserProfile firstUserProfile = userProfileDao.get(firstUser);
        UserProfile secondUserProfile = userProfileDao.get(secondUser);
        chat.setFirstUser(firstUserProfile);
        chat.setSecondUser(secondUserProfile);
        chat.setMessages(new ArrayList<>());
        chatDao.save(chat);
        firstUserProfile.getChats().add(chat);
        secondUserProfile.getChats().add(chat);
        userProfileDao.update(firstUserProfile);
        userProfileDao.update(secondUserProfile);
        return chat;
    }
}

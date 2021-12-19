package com.senla.service;

import com.senla.annotation.Logging;
import com.senla.api.dao.IChatDao;
import com.senla.api.dao.IMessageDao;
import com.senla.api.service.IChatAndMessageService;
import com.senla.api.service.IUserService;
import com.senla.model.Chat;
import com.senla.model.Message;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.ChatDto;
import com.senla.model.dto.MessageDto;
import com.senla.model.dto.filter.ChatFilter;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ChatAndMessageService implements IChatAndMessageService {
    @Autowired
    private IChatDao chatDao;
    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private ExtendedModelMapper modelMapper;
    @Autowired
    private IUserService userService;


    @Override
    @Logging
    public void sendMessage(MessageDto messageDto) {
        UserProfile sender = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        Message message = modelMapper.map(messageDto, Message.class);
        Chat chat = chatDao.get(messageDto.getChat().getId());
        message.setSender(sender);
        message.setCreationDate(LocalDate.now());
        if (sender.getRole().equals(Role.ROLE_USER)) {
            if (chat.getUsers().stream().anyMatch(userProfile -> userProfile.getId().equals(sender.getId()))) {
                messageDao.save(message);
            } else {
                throw new SecurityException("Вы не можете отправлять сообщения в чужие чаты");
            }
        } else if (sender.getRole().equals(Role.ROLE_ADMIN)) {
            if (messageDto.getSender() != null) {
                message.setSender(modelMapper.map(messageDto.getSender(), UserProfile.class));
            }
            if (messageDto.getDate() != null) {
                message.setCreationDate(messageDto.getDate());
            }
            messageDao.save(message);
        }
    }

    @Override
    @Logging
    public void createChat(ChatDto chatDto) {
        UserProfile sender = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        Chat chat = modelMapper.map(chatDto, Chat.class);
        if (sender.getRole().equals(Role.ROLE_USER)) {
            if (chat.getUsers() == null) {
                chat.setUsers(new ArrayList<>());
            }
            chat.getUsers().add(sender);
        }
        chatDao.save(chat);
    }


    @Override
    @Logging
    public List<ChatDto> getByFilter(ChatFilter chatFilter) {
        UserProfile currentUser = modelMapper.map(userService.getCurrentUserProfile(), UserProfile.class);
        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            chatFilter.setUserProfileId(currentUser.getId());
            return modelMapper.mapList(chatDao.getByFilter(chatFilter), ChatDto.class);
        }
        if (chatFilter.getUserProfileId() == null) {
            chatFilter.setUserProfileId(currentUser.getId());
        }
        return modelMapper.mapList(chatDao.getByFilter(chatFilter), ChatDto.class);
    }
}

package com.senla.service;

import com.senla.api.dao.IChatDao;
import com.senla.api.dao.IMessageDao;
import com.senla.api.service.IChatAndMessageService;
import com.senla.model.Chat;
import com.senla.model.Message;
import com.senla.model.Role;
import com.senla.model.UserProfile;
import com.senla.model.dto.ChatDto;
import com.senla.model.dto.MessageDto;
import com.senla.model.dto.filter.ChatFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import com.senla.security.AuthenticationGetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class ChatAndMessageService implements IChatAndMessageService {
    @Autowired
    private IChatDao chatDao;
    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private ModelMapperMapList modelMapper;
    @Autowired
    private AuthenticationGetUser authenticationGetUser;


    @Override
    public void sendMessage(MessageDto messageDto) {
        UserProfile sender = authenticationGetUser.getUserProfileByAuthentication();
        Message message = modelMapper.map(messageDto, Message.class);
        Chat chat = message.getChat();
        if (sender.getRole().equals(Role.ROLE_USER)) {
            if (sender.getChats().stream()
                    .anyMatch(chat1 -> chat1.getId().equals(chat.getId()))) {
                message.setSender(sender);
                message.setCreationDate(LocalDate.now());
            } else {
                throw new SecurityException("Вы не можете редактировать чужие штуки");
            }
        }
        messageDao.save(message);
    }

    @Override
    public void createChat(ChatDto chatDto) {
        UserProfile sender = authenticationGetUser.getUserProfileByAuthentication();
        Chat chat = modelMapper.map(chatDto, Chat.class);
        if (sender.getRole().equals(Role.ROLE_USER)) {
            chat.getUsers().add(sender);
        }
        chatDao.save(chat);
    }


    @Override
    public List<ChatDto> getByFilter(ChatFilter chatFilter) {
        UserProfile user = authenticationGetUser.getUserProfileByAuthentication();
        ChatDto chatDto = modelMapper.map(chatDao.getByFilter(chatFilter).stream().findFirst().orElse(null), ChatDto.class);
        if (user.getRole().equals(Role.ROLE_USER)) {
            if (user.getChats().stream().anyMatch(chat -> chat.getId().equals(chatDto.getId()))) {
                return Collections.singletonList(chatDto);
            } else {
                throw new SecurityException("Вы не можете смотреть чужие штуки");
            }
        }
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            return modelMapper.mapList(chatDao.getByFilter(chatFilter), ChatDto.class);
        }
        return null;
    }
}

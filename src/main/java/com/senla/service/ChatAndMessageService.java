package com.senla.service;

import com.senla.api.dao.IChatDao;
import com.senla.api.dao.IMessageDao;
import com.senla.api.service.IChatAndMessageService;
import com.senla.model.Chat;
import com.senla.model.Message;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.ChatDto;
import com.senla.model.dto.MessageDto;
import com.senla.model.dto.filter.ChatFilter;
import com.senla.modelMapperMethods.ModelMapperMapList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.time.LocalDate;
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


    @Override
    public void sendMessage(Long chatId, MessageDto messageDto) {

        Chat chat = new Chat();
        chat.setId(chatId);
        Message message = modelMapper.map(messageDto, Message.class);
        message.setChat(chat);
        message.setCreationDate(LocalDate.now());
        messageDao.save(message);
    }

    @Override
    public void createChat(ChatDto chatDto) {

        Chat dtoChat = modelMapper.map(chatDto, Chat.class);
        chatDao.save(dtoChat);
    }


    @Override
    public List<ChatDto> getByFilter(ChatFilter chatFilter) {
        return modelMapper.mapList(chatDao.getByFilter(chatFilter), ChatDto.class);
    }
}

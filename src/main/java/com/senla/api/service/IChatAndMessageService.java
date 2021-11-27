package com.senla.api.service;

import com.senla.model.dto.ChatDto;
import com.senla.model.dto.MessageDto;
import com.senla.model.dto.filter.ChatFilter;

import java.util.List;

public interface IChatAndMessageService {

    void sendMessage(MessageDto messageDto);

    void createChat(ChatDto chatDto);

    List<ChatDto> getByFilter(ChatFilter chatFilter);
}

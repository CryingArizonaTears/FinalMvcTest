package com.senla.api.service;

import com.senla.model.Chat;

public interface IChatAndMessageService {

    void sendMessage(Long chatId, Long userId, String text);

    Chat createChat(Long firstUser, Long secondUser);
}

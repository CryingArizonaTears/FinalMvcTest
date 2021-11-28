package com.senla.controllers;

import com.senla.api.service.IChatAndMessageService;
import com.senla.model.dto.ChatDto;
import com.senla.model.dto.MessageDto;
import com.senla.model.dto.filter.ChatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chats")
public class ChatController {

    @Autowired
    private IChatAndMessageService chatAndMessageService;

    @PostMapping()
    public ResponseEntity<Void> createChat(@RequestBody ChatDto chatDto) {
        chatAndMessageService.createChat(chatDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/message")
    public ResponseEntity<Void> createMessage(@RequestBody MessageDto messageDto) {
        chatAndMessageService.sendMessage(messageDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ChatDto>> getChatByFilter(ChatFilter chatFilter) {
        return ResponseEntity.ok(chatAndMessageService.getByFilter(chatFilter));
    }

}

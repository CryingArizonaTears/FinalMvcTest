package com.senla.service;

import com.senla.api.dao.IChatDao;
import com.senla.api.dao.IMessageDao;
import com.senla.api.service.IUserAuthenticationService;
import com.senla.model.Chat;
import com.senla.model.Role;
import com.senla.model.dto.ChatDto;
import com.senla.model.dto.MessageDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.modelMapperMethods.ExtendedModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ChatAndMessageServiceTest {

    @InjectMocks
    private ChatAndMessageService chatAndMessageService;
    @Mock
    private IChatDao chatDao;
    @Mock
    private IMessageDao messageDao;
    @Spy
    private ExtendedModelMapper modelMapper;
    @Mock
    private IUserAuthenticationService userAuthenticationService;

    private final UserProfileDto userProfile = new UserProfileDto();
    private final ChatDto chatDto = new ChatDto();
    private final MessageDto messageDto = new MessageDto();

    @BeforeEach
    void beforeTests() {
        List<MessageDto> messages = new ArrayList<>();
        List<UserProfileDto> userProfiles = new ArrayList<>();
        userProfiles.add(userProfile);
        messageDto.setId(1L);
        messageDto.setText("testText");
        messageDto.setChat(chatDto);
        messages.add(messageDto);
        userProfile.setId(1L);
        userProfile.setRole(Role.ROLE_USER);
        chatDto.setId(1L);
        chatDto.setName("testName");
        chatDto.setMessages(messages);
        chatDto.setUsers(userProfiles);


        Mockito.when(userAuthenticationService.getCurrentUserProfile()).thenReturn(userProfile);
        Chat chat = modelMapper.map(chatDto, Chat.class);
        Mockito.when(chatDao.get(Mockito.any())).thenReturn(chat);

        chatAndMessageService.createChat(chatDto);
        chatAndMessageService.sendMessage(messageDto);

    }

    @Test
    void testSendMessage_Successful() {
        Mockito.verify(messageDao).save(ArgumentMatchers.argThat(messageForSave ->
                messageForSave.getId().equals(messageDto.getId()) &&
                messageForSave.getText().equals(messageDto.getText()) &&
                messageForSave.getCreationDate().equals(LocalDate.now())));
    }

    @Test
    void testCreateChat_Successful() {
        Mockito.verify(chatDao).save(ArgumentMatchers.argThat(chatForSave ->
                chatForSave.getId().equals(chatDto.getId()) &&
                chatForSave.getName().equals(chatDto.getName())));
    }
}
package zerobase.matching.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.dto.ChatDto;
import zerobase.matching.chat.repository.ChatRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void createChat(UserChatRoom userChatRoom, ChatRoom chatRoom, UserEntity sender, ChatDto chatDto){

        // 채팅 생성
        Chat chat = Chat.builder()
                .chatCreateDate(Timestamp.valueOf(LocalDateTime.now()))
                .userChatRoom(userChatRoom)
                .chatContext(chatDto.getChatContext())
                .chatType(chatDto.getChatType())
                .build();

        // 채팅 저장
        chatRepository.save(chat);

    }
}

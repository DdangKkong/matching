package zerobase.matching.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.dto.ChatDto;
import zerobase.matching.chat.repository.ChatRepository;
import zerobase.matching.user.persist.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void createChat(ChatRoom chatRoom, UserEntity sender, ChatDto chatDto){

        // 채팅 생성
        Chat chat = Chat.builder()
                .user(sender)
                .chatContext(chatDto.getChatContext())
                .chatRoom(chatRoom)
                .chatType(chatDto.getChatType())
                .build();

        // 채팅 저장
        chatRepository.save(chat);

    }
}

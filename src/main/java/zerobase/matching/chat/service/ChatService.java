package zerobase.matching.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.User;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.entity.dto.ChatDto;
import zerobase.matching.chat.repository.ChatRepository;
import zerobase.matching.chat.repository.UserChatRoomRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void createChat(ChatDto chatDto){

        // userChatRoom의 id로 userChatRoom table을 가져와서 해당 chat에 넣는다.
        User sender = new User(chatDto.getUserId());
        ChatRoom chatRoom = new ChatRoom();

        Chat chat = Chat.builder()
                .user(sender)
                .chatContext(chatDto.getChatContext())
                .chatRoom(chatRoom)
                .chatType(chatDto.getChatType())
                .build();

        chatRepository.save(chat);

    }
}

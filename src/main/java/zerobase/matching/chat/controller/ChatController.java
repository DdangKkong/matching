package zerobase.matching.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.User;
import zerobase.matching.chat.entity.dto.ChatDto;
import zerobase.matching.chat.entity.type.ChatType;
import zerobase.matching.chat.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void sendChat(ChatDto chatDto){

        // 변경필수
        ChatRoom chatRoom = new ChatRoom();
        User sender = new User();

        // /chat/message 로 받은 경로에서
        // /topic/chat/room/{chatroomId} 경로로 변경해준다.
        if(chatDto.getChatType().equals(ChatType.ENTER)){
            chatDto.setChatContext(chatDto.getUserId() + "님이 입장하였습니다.");
        } else if(chatDto.getChatType().equals(ChatType.EXIT)){
            chatDto.setChatContext(chatDto.getUserId() + "님이 퇴장하였습니다.");
        }

        chatService.createChat(chatDto);

        sendingOperations.convertAndSend(
                "/topic/chat/room/" + chatDto.getChatRoomId(),
                chatDto);
    }
}

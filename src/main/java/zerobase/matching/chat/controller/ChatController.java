package zerobase.matching.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.dto.ChatDto;
import zerobase.matching.chat.entity.type.ChatType;
import zerobase.matching.chat.service.ChatRoomService;
import zerobase.matching.chat.service.ChatService;
import zerobase.matching.user.persist.entity.UserEntity;
import zerobase.matching.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @MessageMapping("/chat/message")
    public void sendChat(ChatDto chatDto){

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatDto.getChatRoomId());
        UserEntity sender = userService.findUser(chatDto.getUserId());

        // ENTER 값이 있다면 내용에 ~ 입장하였습니다 저장, EXIT 값이 있다면 ~ 퇴장하였습니다 저장
        if(chatDto.getChatType().equals(ChatType.ENTER)){
            chatDto.setChatContext(chatDto.getUserId() + "님이 입장하였습니다.");

        } else if(chatDto.getChatType().equals(ChatType.EXIT)){
            chatDto.setChatContext(chatDto.getUserId() + "님이 퇴장하였습니다.");
        }

        // 채팅 저장
        chatService.createChat(chatRoom, sender, chatDto);

        // /chat/message 로 받은 경로에서
        // /topic/chat/room/{chatroomId} 경로로 변경해준다.
        sendingOperations.convertAndSend(
                "/topic/chat/room/" + chatDto.getChatRoomId(),
                chatDto);
    }
}

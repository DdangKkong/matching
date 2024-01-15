package zerobase.matching.chat.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.dto.ChatDto;
import zerobase.matching.chat.type.ChatType;
import zerobase.matching.chat.service.ChatRoomService;
import zerobase.matching.chat.service.ChatService;
import zerobase.matching.user.persist.entity.UserEntity;
import zerobase.matching.user.service.UserService;

import java.util.logging.SocketHandler;

@RestController
@RequiredArgsConstructor
public class ChatController {

//    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
// test
//    @EventListener
//    public void handleWebSocketConnectListner(SessionConnectEvent event){
//        LOGGER.info("Received a new web socket connection");
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccesor.getSessionId();
//
//        LOGGER.info("sessionId Disconnected : " + sessionId);
//    }
    @MessageMapping("/chat/message")
    public void sendChat(ChatDto chatDto){

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatDto.getRoomId());
        UserEntity sender = userService.findUser(chatDto.getUserId());

        // ENTER 값이 있다면 내용에 ~ 입장하였습니다 저장, EXIT 값이 있다면 ~ 퇴장하였습니다 저장
        if(chatDto.getChatType().equals(ChatType.ENTER)){
            chatDto.setChatContext(chatDto.getUserId() + "님이 입장하였습니다.");

        } else if(chatDto.getChatType().equals(ChatType.EXIT)){
            chatDto.setChatContext(chatDto.getUserId() + "님이 퇴장하였습니다.");
        }

        UserChatRoom userChatRoom = chatRoomService.findUserChatRoom(chatDto.getRoomId(),
                chatDto.getUserId());
        // 채팅 저장
        chatService.createChat(userChatRoom, chatRoom, sender, chatDto);

        // /chat/message 로 받은 경로에서
        // /topic/chat/room/{chatroomId} 경로로 변경해준다.
        sendingOperations.convertAndSend(
                "/sub/chat/room/" + chatDto.getRoomId(),
                chatDto);
    }




}

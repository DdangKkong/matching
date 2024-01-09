package zerobase.matching.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.User;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.entity.dto.ChatRoomDto;
import zerobase.matching.chat.service.ChatRoomService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/chat") // 공통부분은 requestMapping을 한다.
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Void> createChatRoom(
            @PathVariable("userId") Long userId, @RequestParam String name){

        User user = new User(userId);

        chatRoomService.createChatRoom(user, name);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/rooms/{userId}")
    public ResponseEntity<List<ChatRoomDto>> getChatRoomList(
            @PathVariable("userId") Long userId){

        // userChatRoomDto로 받은 list를 chatRoomDto로 변환해준다.
        return ResponseEntity.ok(chatRoomService.findAllByUserId(userId)
                .stream()
                .map(userChatRoom -> {
                    return ChatRoomDto.toDto(userChatRoom.getChatRoom());
                }).toList());

//        ResponseEntity.ok(chatRoomService.findAllByUserId(userId)
//                .stream()
//                .map(userChatRoom ->
//                        ChatRoomDto.toDto(userChatRoom.getChatRoom())
//                ).collect(Collectors.toList()));
    }

    @PostMapping("/enter/{chatRoomId}/user/{userId}")
    public ResponseEntity<Void> enterChatRoom(@PathVariable Long chatRoomId,
                                              @PathVariable Long userId){

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatRoomId);
        User user = new User(userId);

        chatRoomService.enterChatRoom(chatRoom, user);

        return ResponseEntity.status(OK).build();
    }

    @PostMapping("/delete/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long chatRoomId){

        chatRoomService.deleteChatRoom(chatRoomId);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/exit/{chatRoomId}/user/{userId}")
    public ResponseEntity<Void> exitChatRoom(@PathVariable Long chatRoomId,
                                             @PathVariable Long userId){

        chatRoomService.exitChatRoom(chatRoomId, userId);

        return ResponseEntity.status(NO_CONTENT).build();
    }
}

package zerobase.matching.chat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.matching.chat.dto.IdDto;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.dto.ChatRoomDto;
import zerobase.matching.chat.service.ChatRoomService;
import zerobase.matching.user.persist.entity.UserEntity;
import zerobase.matching.user.service.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/chat") // 공통부분은 requestMapping을 한다.
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService, UserService userService) {
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    //@PathVariable("userId") Long userId
    @PostMapping("/create")
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid IdDto.UserRequest request){

        UserEntity user = userService.findUser(request.getUserId());

        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getName());
        chatRoomService.createUserChatRoom(chatRoom, user);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRoomList(
            @RequestBody @Valid IdDto.UserRequest request){

        // userChatRoomDto로 받은 list를 chatRoomDto로 변환해준다.
        return ResponseEntity.ok(chatRoomService.findAllByUserId(request.getUserId())
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

    @PostMapping("/enter")
    public ResponseEntity<Void> enterChatRoom(@RequestBody @Valid IdDto.UserRequest request){

        ChatRoom chatRoom = chatRoomService.findChatRoom(request.getChatRoomId());
        UserEntity user = userService.findUser(request.getUserId());
        chatRoomService.enterChatRoom(chatRoom, user);

        return ResponseEntity.status(OK).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteChatRoom(@RequestBody @Valid IdDto.UserRequest request){

        chatRoomService.deleteChatRoom(request.getChatRoomId());

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/exit")
    public ResponseEntity<Void> exitChatRoom(@RequestBody @Valid IdDto.UserRequest request){

        chatRoomService.exitChatRoom(request.getChatRoomId(), request.getUserId());

        return ResponseEntity.status(NO_CONTENT).build();
    }
}

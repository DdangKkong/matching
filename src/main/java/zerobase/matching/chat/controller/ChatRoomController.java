package zerobase.matching.chat.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import zerobase.matching.chat.dto.ChatRoomDto;
import zerobase.matching.chat.dto.IdDto;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.service.ChatRoomService;
import zerobase.matching.user.persist.entity.UserEntity;
import zerobase.matching.user.service.UserService;

@Controller
@RequestMapping("/maching/chat") // 공통부분은 requestMapping을 한다.
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid IdDto.UserRequest request){

        UserEntity user = userService.findUser(request.getUserId());

        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getName());
        chatRoomService.createUserChatRoom(chatRoom, user);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRoomList(
            @RequestBody @Valid IdDto.UserRequest request) {

        // userChatRoomDto로 받은 list를 chatRoomDto로 변환해준다.
        return ResponseEntity.ok(chatRoomService.findAllByUserId(request.getUserId())
            .stream()
            .map(userChatRoom -> {
                return ChatRoomDto.toDto(userChatRoom.getChatRoom());
            }).toList());
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

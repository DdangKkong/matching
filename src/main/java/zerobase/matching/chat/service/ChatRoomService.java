package zerobase.matching.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.User;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.entity.dto.ChatRoomDto;
import zerobase.matching.chat.repository.ChatRoomRepository;
import zerobase.matching.chat.repository.UserChatRoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    public void createChatRoom(User user, String name){

        // 변경필수
        ChatRoom chatRoom = ChatRoom.builder()
                .title(name)
                .build();

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

        userChatRoomRepository.save(userChatRoom);
        chatRoomRepository.save(chatRoom);
    }

    public List<UserChatRoom> findAllByUserId(Long userId){

        return userChatRoomRepository.findAllByUserId(userId);
    }

    public ChatRoom findChatRoom(Long chatRoomId){
        return chatRoomRepository.findByChatroomId(chatRoomId).orElseThrow(
                () -> new RuntimeException("ChatRoom doesn't exist"));

    }
    public void enterChatRoom(ChatRoom chatRoom, User user){
        Optional<UserChatRoom> userChatRoomOptional =
                userChatRoomRepository.findByUserIdAndChatRoomId(user.userId, chatRoom.getChatroomId());

        // 해당 userId와 chatRoomId가 일치하는 UserChatRoom이 있는지 확인해보고 없으면 추가해준다.
        // 있으면 채팅방에 들어가게 해준다.
        // 해당 유저를 기존 채팅방에 추가해주는 개념

        if(userChatRoomOptional.isEmpty()){
            userChatRoomRepository.save(UserChatRoom.builder()
                            .chatRoom(chatRoom)
                            .user(user)
                            .build());
        }

    }

    public void deleteChatRoom(Long chatRoomId){
        List<UserChatRoom> allByChatRoom = userChatRoomRepository.findAllByChatRoomId(chatRoomId);
        ChatRoom chatRoom = findChatRoom(chatRoomId);

        userChatRoomRepository.deleteAll(allByChatRoom);
        chatRoomRepository.delete(chatRoom);
    }

    public void exitChatRoom(Long chatRoomId, Long userId){
        List<UserChatRoom> userChatRooms = userChatRoomRepository.findAllByChatRoomId(chatRoomId);

        UserChatRoom userChatRoom = userChatRoomRepository.
                findByUserIdAndChatRoomId(chatRoomId, userId).orElseThrow(
                () -> new RuntimeException("User isn't in ChatRoom")
        );
        ChatRoom chatRoom = findChatRoom(chatRoomId);

        // 해당 chatroom에 있는 인원이 한 개일 때, 채팅방 자체를 없앤다.
        if (userChatRooms.size() == 1) {
            userChatRoomRepository.delete(userChatRoom);
            chatRoomRepository.delete(chatRoom);

        } else if(userChatRooms.size() > 1){
            userChatRoomRepository.delete(userChatRoom);
        }
    }
}

package zerobase.matching.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.repository.ChatRoomRepository;
import zerobase.matching.chat.repository.UserChatRoomRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    public void createChatRoom(UserEntity user, String name){
        // 채팅방을 만든다.

        // 채팅방 인스턴스 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .title(name)
                .build();

        // 해당 user의 userChatRoom 생성
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

        // userChatroom 저장
        userChatRoomRepository.save(userChatRoom);
        // chatRoom 저장
        chatRoomRepository.save(chatRoom);
    }

    public List<UserChatRoom> findAllByUserId(Long userId){
        // 해당 user의 UserChatRoom 찾기
        return userChatRoomRepository.findAllByUserId(userId);
    }

    public ChatRoom findChatRoom(Long chatRoomId){
        // 채팅방 찾아주는 기능
        return chatRoomRepository.findByChatroomId(chatRoomId).orElseThrow(
                () -> new RuntimeException("ChatRoom doesn't exist"));

    }
    public void enterChatRoom(ChatRoom chatRoom, UserEntity user){
        Optional<UserChatRoom> userChatRoomOptional =
                userChatRoomRepository.findByUserIdAndChatRoomId(user.getUserId(), chatRoom.getChatroomId());

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
        // 해당 채팅방 자체를 삭제 해버린다.

        List<UserChatRoom> allByChatRoom = userChatRoomRepository.findAllByChatRoomId(chatRoomId);
        ChatRoom chatRoom = findChatRoom(chatRoomId);

        // 유저들 - 해당 채팅방 모두 삭제
        userChatRoomRepository.deleteAll(allByChatRoom);
        // 해당 채팅방 삭제
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

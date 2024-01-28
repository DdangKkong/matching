package zerobase.matching.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.repository.ChatRoomRepository;
import zerobase.matching.chat.repository.UserChatRoomRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

private final ChatRoomRepository chatRoomRepository;
private final UserChatRoomRepository userChatRoomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserChatRoomRepository userChatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userChatRoomRepository = userChatRoomRepository;
    }

    public ChatRoom createChatRoom(String name){
        // 채팅방을 만든다.

        // 채팅방 인스턴스 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomCreateDate(Timestamp.valueOf(LocalDateTime.now()))
                .title(name)
                .build();

        // chatRoom 저장
        return chatRoomRepository.save(chatRoom);
    }

    public void createUserChatRoom(ChatRoom chatRoom, UserEntity user){


        // 해당 user의 userChatRoom 생성
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

        // userChatroom 저장
        userChatRoomRepository.save(userChatRoom);
    }



    public List<UserChatRoom> findAllByUserId(Long userId){
        // 해당 user의 UserChatRoom 찾기
        return userChatRoomRepository.findAllByUserId(userId);
    }

    public ChatRoom findChatRoom(Long chatRoomId){
        // 채팅방 찾아주는 기능
        return chatRoomRepository.findByChatroomId(chatRoomId).orElseThrow(
                () -> new RuntimeException("ChatRoom doesn't exist")
        );

    }

//    public UserChatRoom findUserChatRoom(Long userChatRoomId){
//
//        return userChatRoomRepository.findByUserChatRoomId(userChatRoomId).orElseThrow(
//                () -> new RuntimeException("UserChatRoom doesn't exist")
//        );
//    }

    public UserChatRoom findUserChatRoom(Long chatRoomId, Long userId){
        return userChatRoomRepository.findByUserIdAndChatRoomId(userId, chatRoomId).orElseThrow(
                ()->new RuntimeException("UserChatRoom doesn't exist")
        );
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

        ChatRoom chatRoom = findChatRoom(chatRoomId);
        List<UserChatRoom> allByChatRoom = userChatRoomRepository.findAllByChatRoom(chatRoom);


        // 유저들 - 해당 채팅방 모두 삭제
        if(!allByChatRoom.isEmpty()){
            userChatRoomRepository.deleteAll(allByChatRoom);
        }
        // 해당 채팅방 삭제
        chatRoomRepository.delete(chatRoom);
    }

    public void exitChatRoom(Long chatRoomId, Long userId){
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        List<UserChatRoom> userChatRooms = userChatRoomRepository.findAllByChatRoom(chatRoom);

        UserChatRoom userChatRoom = userChatRoomRepository.
                findByUserIdAndChatRoomId(userId, chatRoomId).orElseThrow(
                () -> new RuntimeException("User isn't in ChatRoom")
        );


        // 해당 chatroom에 있는 인원이 한 명일 때, 채팅방 자체를 없앤다.

        if (userChatRooms.size() == 1) {
            userChatRoomRepository.delete(userChatRoom);
            chatRoomRepository.delete(chatRoom);

        } else if(userChatRooms.size() > 1){
            userChatRoomRepository.delete(userChatRoom);
        }
    }
}

package zerobase.matching.chat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.repository.ChatRepository;
import zerobase.matching.chat.repository.ChatRoomRepository;
import zerobase.matching.chat.repository.UserChatRoomRepository;
import zerobase.matching.user.exception.CustomException;
import zerobase.matching.user.exception.ErrorCode;
import zerobase.matching.user.persist.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

private final ChatRoomRepository chatRoomRepository;
private final UserChatRoomRepository userChatRoomRepository;
private final ChatRepository chatRepository;

    public ChatRoom createChatRoom(String name){

        // 채팅방 인스턴스 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomCreateDate(LocalDateTime.now())
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

    public List<UserChatRoom> findAllByUserId(int userId){
        // 해당 user의 UserChatRoom 찾기
        return userChatRoomRepository.findAllByUserId(userId);
    }

    public ChatRoom findChatRoom(int chatRoomId){
        // 채팅방 찾아주는 기능
        return chatRoomRepository.findByChatroomId(chatRoomId).orElseThrow(
                () -> new CustomException(ErrorCode.CHAT_ROOM_INVALID)
        );
    }

    // 채팅 알림을 보내기 위해, 같은 채팅방 구독한 인원을 List 화 한다.
    public List<Integer> findUserIdList(int chatRoomId) {
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        List<Chat> allByChatRoom = chatRepository.findAllByChatRoom(chatRoom);

        return allByChatRoom.stream()
            .map(Chat -> Chat.getUser().getUserId())
            .collect(Collectors.toList());
    }

    public UserChatRoom findUserChatRoom(int chatRoomId, int userId){
        return userChatRoomRepository.findByUserIdAndChatRoomId(userId, chatRoomId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_CHAT_ROOM_INVALID)
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

    public void deleteChatRoom(int chatRoomId){

        ChatRoom chatRoom = findChatRoom(chatRoomId);
        List<UserChatRoom> allByChatRoom = userChatRoomRepository.findAllByChatRoom(chatRoom);

        // 유저들 - 해당 채팅방 모두 삭제
        if(!allByChatRoom.isEmpty()){
            userChatRoomRepository.deleteAll(allByChatRoom);
        }
        // 해당 채팅방 삭제
        chatRoomRepository.delete(chatRoom);
    }

    public void exitChatRoom(int chatRoomId, int userId){
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        List<UserChatRoom> userChatRooms = userChatRoomRepository.findAllByChatRoom(chatRoom);

        UserChatRoom userChatRoom = userChatRoomRepository.
                findByUserIdAndChatRoomId(userId, chatRoomId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_IN_CHAT_ROOM_NOTFOUND)
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

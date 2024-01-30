package zerobase.matching.chat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;
import zerobase.matching.chat.repository.ChatRoomRepository;
import zerobase.matching.chat.repository.UserChatRoomRepository;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @InjectMocks
    private ChatRoomService chatRoomService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private UserChatRoomRepository userChatRoomRepository;

    @Test
    void createChatRoom() {

//        ChatRoom chatRoom = ChatRoom.builder()
//                .chatroomId(10)
//                .chatRoomCreateDate(Timestamp.valueOf(LocalDateTime.now()))
//                .title("chatroom1")
//                .build();

        // when
         chatRoomService.createChatRoom("chatroom1" );

         //then
        // verify : 오른쪽 함수가 몇번 실행되었는가 확인
         verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));

    }

    @Test
    void createUserChatRoom() {

        //given
        ChatRoom chatRoom = ChatRoom.builder()
            .chatroomId(10)
            .chatRoomCreateDate(Timestamp.valueOf(LocalDateTime.now()))
            .title("chatroom1")
            .build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .build();

//        given(chatRoomRepository.findById(10))
//                .willReturn(Optional.of(chatRoom));
//        given(userRepository.findById(10))
//                .willReturn(Optional.of(user));

        // when
        chatRoomService.createUserChatRoom(chatRoom, user);

        //then
        verify(userChatRoomRepository, times(1)).save(any(UserChatRoom.class));
    }

    @Test
    // 리스트 확인하는 방법이 이렇게 하는게 맞는지 궁금
    void findAllByUserId() {
        UserEntity user1 = UserEntity.builder().userId(10).build();

        UserChatRoom userChatRoom1 = UserChatRoom.builder()
                .user(user1)
                .userChatRoomId(100)
                .build();

        UserChatRoom userChatRoom2 = UserChatRoom.builder()
                .user(user1)
                .userChatRoomId(200)
                .build();

        List<UserChatRoom> userChatRooms = Arrays.asList(userChatRoom1, userChatRoom2);

        given(userChatRoomRepository.findAllByUserId(10)).willReturn(userChatRooms);

        // when
        List<UserChatRoom> userChatRooms2 = chatRoomService.findAllByUserId(10);

        // then
        assertThat(userChatRooms2.size()).isEqualTo(2);
        assertThat(userChatRooms2.get(0).getUserChatRoomId()).isEqualTo(userChatRoom1.getUserChatRoomId());
        assertThat(userChatRooms2.get(1).getUserChatRoomId()).isEqualTo(userChatRoom2.getUserChatRoomId());

        // assertThatThrownBy : 예외가 던져지지 않으면 실패
        // assertThatCode : 예외가 던져지지 않아도 실패하지 않고, 에러 메세지도 뜨기 때문에 고치기 쉽다.

    }


    @Test
    void findChatRoom() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .chatRoomCreateDate(Timestamp.valueOf(LocalDateTime.now()))
                .title("chatroom1")
                .build();

        given(chatRoomRepository.findByChatroomId(10)).willReturn(Optional.of(chatRoom));

        // when & then
        assertThat(chatRoomService.findChatRoom(10)).isEqualTo(chatRoom);

    }

    @Test
    void findUserChatRoom() {
        UserEntity user = UserEntity.builder()
                .userId(10)
                .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .build();

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .userChatRoomId(10)
                .user(user)
                .chatRoom(chatRoom)
                .build();

        given(userChatRoomRepository.findByUserIdAndChatRoomId(anyInt(),anyInt())).willReturn(Optional.of(userChatRoom));

        // when & then
        assertThat(chatRoomService.findUserChatRoom(10, 10)).isEqualTo(userChatRoom);
    }

    @Test
    @DisplayName("UserChatRoom에 해당 userchatroom이 없을 때(비어있을 때)")
    void enterChatRoom1() {
        // given
        UserEntity user = UserEntity.builder()
                .userId(10)
                .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .build();

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .userChatRoomId(10)
                .build();

        // 해당 함수가 실행될 때 함수가 비어있도록 만든다.
        given(userChatRoomRepository.findByUserIdAndChatRoomId(anyInt(),anyInt()))
                .willReturn(empty());

        // then

        chatRoomService.enterChatRoom(chatRoom, user);
        // 비어있게 만들어서 enterChatRoom 속 isEmpty 조건에 부합해서 save함수가 실행된다.
        verify(userChatRoomRepository, times(1)).save(any(UserChatRoom.class));

    }

    @Test
    @DisplayName("UserChatRoom에 해당 userchatroom이 있을 때)")
    void enterChatRoom2() {
        // given
        UserEntity user = UserEntity.builder()
                .userId(10)
                .build();

        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .build();

        UserChatRoom userChatRoom = UserChatRoom.builder()
                .chatRoom(chatRoom)
                .user(user)
                .userChatRoomId(10)
                .build();

        // 해당 함수를 실행할 때 userChatRoom이 존재하게 한다.
        given(userChatRoomRepository.findByUserIdAndChatRoomId(anyInt(),anyInt()))
                .willReturn(Optional.of(userChatRoom));

        // then

        chatRoomService.enterChatRoom(chatRoom, user);

        // enterChatRoom 속 userChatRoom이 존재하도록 해서 save함수가 실행되지 않는다.
        verify(userChatRoomRepository, times(0)).save(any(UserChatRoom.class));

    }

    @Test
    void deleteChatRoom() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .build();


        UserEntity user1 = UserEntity.builder()
                .userId(10)
                .build();

        UserEntity user2 = UserEntity.builder()
                .userId(20)
                .build();

        UserChatRoom userChatRoom1 = UserChatRoom.builder()
                .user(user1)
                .chatRoom(chatRoom)
                .userChatRoomId(100)
                .build();

        UserChatRoom userChatRoom2 = UserChatRoom.builder()
                .user(user2)
                .chatRoom(chatRoom)
                .userChatRoomId(200)
                .build();

        List<UserChatRoom> userChatRooms = Arrays.asList(userChatRoom1, userChatRoom2);

        given(chatRoomRepository.findByChatroomId(10)).willReturn(Optional.of(chatRoom));
        given(userChatRoomRepository.findAllByChatRoom(chatRoom)).willReturn(userChatRooms);

        // when
        chatRoomService.deleteChatRoom(10);

        // then
        verify(userChatRoomRepository, times(1)).deleteAll(userChatRooms);
        verify(chatRoomRepository, times(1)).delete(chatRoom);

    }

    @Test
    @DisplayName("채팅방에 인원이 한 명이상일 때")
    void exitChatRoom1() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .build();


        UserEntity user1 = UserEntity.builder()
                .userId(10)
                .build();

        UserEntity user2 = UserEntity.builder()
                .userId(20)
                .build();

        UserChatRoom userChatRoom1 = UserChatRoom.builder()
                .user(user1)
                .chatRoom(chatRoom)
                .userChatRoomId(100)
                .build();

        UserChatRoom userChatRoom2 = UserChatRoom.builder()
                .user(user2)
                .chatRoom(chatRoom)
                .userChatRoomId(200)
                .build();

        List<UserChatRoom> userChatRooms = Arrays.asList(userChatRoom1, userChatRoom2);

        given(chatRoomRepository.findByChatroomId(10)).willReturn(Optional.of(chatRoom));
        given(userChatRoomRepository.findAllByChatRoom(chatRoom)).willReturn(userChatRooms);

        given(userChatRoomRepository.findByUserIdAndChatRoomId(
                userChatRoom1.getChatRoom().getChatroomId(), userChatRoom1.getUser().getUserId()
        )).willReturn(Optional.of(userChatRoom1));

//        given(userChatRoomRepository.findByUserIdAndChatRoomId(
//                userChatRoom2.getChatRoom().getChatroomId(), userChatRoom2.getUser().getUserId()
//        )).willReturn(Optional.of(userChatRoom2));

        // when
        chatRoomService.exitChatRoom(10, 10);

        // then
        verify(userChatRoomRepository, times(1)).delete(userChatRoom1);
          // 인원이 2명 이상일 때는 채팅방을 없애지 않는다.
        verify(chatRoomRepository, times(0)).delete(chatRoom);

    }


    @Test
    @DisplayName("인원이 한 명일 때(채팅방 자체를 없앤다)")
    void exitChatRoom2() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .chatroomId(10)
                .build();


        UserEntity user1 = UserEntity.builder()
                .userId(10)
                .build();

        UserEntity user2 = UserEntity.builder()
                .userId(20)
                .build();

        UserChatRoom userChatRoom1 = UserChatRoom.builder()
                .user(user1)
                .chatRoom(chatRoom)
                .userChatRoomId(100)
                .build();

        UserChatRoom userChatRoom2 = UserChatRoom.builder()
                .user(user2)
                .chatRoom(chatRoom)
                .userChatRoomId(200)
                .build();

        List<UserChatRoom> userChatRooms = Arrays.asList(userChatRoom1);

        given(chatRoomRepository.findByChatroomId(10)).willReturn(Optional.of(chatRoom));
        given(userChatRoomRepository.findAllByChatRoom(chatRoom)).willReturn(userChatRooms);

        given(userChatRoomRepository.findByUserIdAndChatRoomId(
                userChatRoom1.getChatRoom().getChatroomId(), userChatRoom1.getUser().getUserId()
        )).willReturn(Optional.of(userChatRoom1));


        // when
        chatRoomService.exitChatRoom(10, 10);

        // then
        verify(userChatRoomRepository, times(1)).delete(userChatRoom1);
           // 인원이 한 개일 때는 채팅방도 없앤다.
        verify(chatRoomRepository, times(1)).delete(chatRoom);

    }


}





package zerobase.matching.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;

import java.util.List;
import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Integer> {
    // 쿼리 메서드로 변경해서 만들어서 사용해볼것
    @Query("select uc from UserChatRoom uc where uc.user.userId = :userId and uc.chatRoom.chatroomId =:chatRoomId")
    Optional<UserChatRoom> findByUserIdAndChatRoomId(@Param("userId") int userId,@Param("chatRoomId") int chatRoomId);

    @Query("select uc from UserChatRoom uc where uc.user.userId = :userId")
    List<UserChatRoom> findAllByUserId(@Param("userId") int userId);

    List<UserChatRoom> findAllByChatRoom(ChatRoom chatRoom);

}

package zerobase.matching.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zerobase.matching.chat.entity.UserChatRoom;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    // 쿼리 메서드로 변경해서 만들어서 사용해볼것
    @Query("select uc from UserChatRoom uc where uc.user.userId = :userId and uc.chatRoom.chatroomId =:chatRoomId")
    Optional<UserChatRoom> findByUserIdAndChatRoomId(@Param("userId") Long userId,@Param("chatRoomId") Long chatRoomId);

    @Query("select uc from UserChatRoom uc where uc.user.userId = :userId")
    List<UserChatRoom> findAllByUserId(@Param("userId") Long userId);

    List<UserChatRoom> findAllByChatRoomId(Long chatRoomId);
}

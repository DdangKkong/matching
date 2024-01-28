package zerobase.matching.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zerobase.matching.chat.entity.ChatRoom;

import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select c from ChatRoom c where c.chatroomId =:chatRoomId")
    Optional<ChatRoom> findByChatroomId(Long chatRoomId);
}

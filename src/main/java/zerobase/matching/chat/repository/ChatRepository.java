package zerobase.matching.chat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.UserChatRoom;


public interface ChatRepository extends JpaRepository<Chat, Integer> {
  List<Chat> findAllByChatRoom(ChatRoom chatRoom);
}

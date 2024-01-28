package zerobase.matching.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.chat.entity.Chat;


public interface ChatRepository extends JpaRepository<Chat, Long> {
}

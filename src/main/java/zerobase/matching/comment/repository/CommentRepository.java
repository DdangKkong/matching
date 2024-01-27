package zerobase.matching.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.comment.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}

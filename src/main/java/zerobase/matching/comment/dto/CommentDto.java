package zerobase.matching.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.comment.domain.Comment;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentDto {

  // 댓글 id
  private long commentId;

  // 구인 글 id
  private long projectId;

  // 회원 id
  private long userId;

  // 내용
  private String content;

  // 댓글 작성 일시
  private LocalDateTime createTime;

  // 댓글 수정 일시
  private LocalDateTime updateTime;

  // 댓글 삭제 일시
  private LocalDateTime deleteTime;

  // 부모 댓글 고유번호
  private long parentId;

  // 계층
  private int level;


  public static CommentDto fromEntity(Comment comment) {
    return CommentDto.builder()
        .commentId(comment.getCommentId())
        .projectId(comment.getProject().getProjectId())
        .userId(comment.getUser().getUserId())
        .content(comment.getContent())
        .createTime(comment.getCreateTime())
        .updateTime(comment.getUpdateTime())
        .deleteTime(comment.getDeleteTime())
        .parentId(comment.getParentId())
        .level(comment.getLevel())
        .build();
  }

}

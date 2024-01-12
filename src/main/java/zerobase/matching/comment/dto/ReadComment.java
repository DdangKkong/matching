package zerobase.matching.comment.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class ReadComment {


  @Builder
  @Getter // 이거 없으면 403 에러 뜸 ( 클라이언트가 요청한 Type 으로 응답을 내려줄 수 없다 )
  public static class Response {

    private long commentId;
    private long projectId;
    private long userId;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private long parentId;
    private int level;

    public static ReadComment.Response fromEntity(CommentDto commentDto) {
      return ReadComment.Response.builder()
          .commentId(commentDto.getCommentId())
          .projectId(commentDto.getProjectId())
          .userId(commentDto.getUserId())
          .content(commentDto.getContent())
          .createTime(commentDto.getCreateTime())
          .updateTime(commentDto.getUpdateTime())
          .deleteTime(commentDto.getDeleteTime())
          .parentId(commentDto.getParentId())
          .level(commentDto.getLevel())
          .build();
    }

  }

}

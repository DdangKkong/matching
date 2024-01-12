package zerobase.matching.comment.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class DeleteComment {

  @Getter
  public static class Request {

    @NotNull
    private long userId;

  }

  @Builder
  @Getter
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

    public static Response fromEntity(CommentDto commentDto){
      return Response.builder()
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

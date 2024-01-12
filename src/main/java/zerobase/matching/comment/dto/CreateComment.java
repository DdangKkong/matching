package zerobase.matching.comment.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class CreateComment {

  @Getter
  public static class Request {

    @NotNull
    private long projectId;
    @NotNull
    private long userId;
    @NotNull
    private String content;

    private long parentId;

    private int level;
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

    public static CreateComment.Response fromEntity(CommentDto commentDto) {
      return CreateComment.Response.builder()
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

package zerobase.matching.comment.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class Recomment {

  @Getter
  public static class Request {

    @NotNull
    private long parentId;
    @NotNull
    private String content;
  }

  @Builder
  @Getter
  public static class Response {

    private long commentId;
    private long projectId;
    private long userId;
    private String content;
    private LocalDateTime createTime;
    private long parentId;
    private int level;

    public static Response fromEntity(CommentDto commentDto){
      return Response.builder()
          .commentId(commentDto.getCommentId())
          .projectId(commentDto.getProjectId())
          .userId(commentDto.getUserId())
          .content(commentDto.getContent())
          .createTime(commentDto.getCreateTime())
          .parentId(commentDto.getParentId())
          .level(commentDto.getLevel())
          .build();
    }

  }

}

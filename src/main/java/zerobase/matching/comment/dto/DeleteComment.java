package zerobase.matching.comment.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class DeleteComment {
  @Builder
  @Getter
  public static class Response {

    private int commentId;
    private int projectId;
    private int userId;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;
    private int parentId;
    private int level;

    public static Response fromEntity(CommentDto commentDto){
      return Response.builder()
          .commentId(commentDto.getCommentId())
          .projectId(commentDto.getProjectId())
          .userId(commentDto.getUserId())
          .content(commentDto.getContent())
          .createdTime(commentDto.getCreatedTime())
          .updatedTime(commentDto.getUpdatedTime())
          .deletedTime(commentDto.getDeletedTime())
          .parentId(commentDto.getParentId())
          .level(commentDto.getLevel())
          .build();
    }

  }

}

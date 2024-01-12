package zerobase.matching.project.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class DeleteProject {

  @Getter
  public static class Request {

    @NotNull
    private long userId;

  }

  @Builder
  @Getter
  public static class Response {

    private long projectId;
    private long userId;
    private String title;
    private String content;
    private String projectOnOffline;
    private String place;
    private int numberOfRecruit;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDate dueDate;

    public static Response fromEntity(ProjectDto projectDto){
      return Response.builder()
          .projectId(projectDto.getProjectId())
          .userId(projectDto.getUserId())
          .title(projectDto.getTitle())
          .content(projectDto.getContent())
          .projectOnOffline(projectDto.getProjectOnOffline().toString())
          .place(projectDto.getPlace())
          .numberOfRecruit(projectDto.getNumberOfRecruit())
          .createTime(projectDto.getCreateTime())
          .updateTime(projectDto.getUpdateTime())
          .dueDate(projectDto.getDueDate())
          .build();
    }

  }

}

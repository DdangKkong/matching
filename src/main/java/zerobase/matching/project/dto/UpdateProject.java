package zerobase.matching.project.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import zerobase.matching.project.domain.ProjectOnOffline;

public class UpdateProject {

  @Getter
  public static class Request {

    @NotNull
    private long userId;

    @NotNull
    private long projectId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private ProjectOnOffline projectOnOffline;

    @NotNull
    private String place;

    @NotNull
    private int numberOfRecruit;

    @NotNull
    private LocalDate dueDate;

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
          .updateTime(LocalDateTime.now())
          .dueDate(projectDto.getDueDate())
          .build();
    }

  }

}

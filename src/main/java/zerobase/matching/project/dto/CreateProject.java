package zerobase.matching.project.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import zerobase.matching.project.domain.ProjectOnOffline;

public class CreateProject {

  @Getter
  public static class Request {

    @NotNull
    private BigInteger userId;

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
  public static class Response {

    private BigInteger projectId;
    private BigInteger userId;
    private String title;
    private String content;
    private ProjectOnOffline projectOnOffline;
    private String place;
    private int numberOfRecruit;
    private LocalDateTime createTime;
    private LocalDate dueDate;

    public static Response fromEntity(ProjectDto projectDto){
      return Response.builder()
          .projectId(projectDto.getProjectId())
          .userId(projectDto.getUserId())
          .title(projectDto.getTitle())
          .content(projectDto.getContent())
          .projectOnOffline(projectDto.getProjectOnOffline())
          .numberOfRecruit(projectDto.getNumberOfRecruit())
          .createTime(LocalDateTime.now())
          .dueDate(projectDto.getDueDate())
          .build();
    }

  }

}

package zerobase.matching.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import zerobase.matching.domain.ProjectOnOffline;

public class DeleteProject {

  @Getter
  public static class Request {

    @NotNull
    private BigInteger userId;

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
    private LocalDateTime updateTime;
    private LocalDate dueDate;

    public static Response fromEntity(ProjectDto projectDto){
      return Response.builder()
          .projectId(projectDto.getProjectId())
          .userId(projectDto.getUserId())
          .title(projectDto.getTitle())
          .content(projectDto.getContent())
          .projectOnOffline(projectDto.getProjectOnOffline())
          .numberOfRecruit(projectDto.getNumberOfRecruit())
          .createTime(projectDto.getCreateTime())
          .updateTime(projectDto.getUpdateTime())
          .dueDate(projectDto.getDueDate())
          .build();
    }

  }

}

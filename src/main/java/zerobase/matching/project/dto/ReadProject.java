package zerobase.matching.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class ReadProject {


  @Builder
  @Getter // 이거 없으면 403 에러 뜸 ( 클라이언트가 요청한 Type 으로 응답을 내려줄 수 없다 )
  public static class Response {

    private long projectId;
    private long userId;
    private String title;
    private String content;
    private String projectOnOffline;
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
          .projectOnOffline(projectDto.getProjectOnOffline().toString())
          .place(projectDto.getPlace())
          .numberOfRecruit(projectDto.getNumberOfRecruit())
          .createTime(projectDto.getCreateTime())
          .dueDate(projectDto.getDueDate())
          .build();
    }

  }

}

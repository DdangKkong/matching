package zerobase.matching.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.recruitment.dto.RecruitmentDto;

public class DeleteProject {

  @Builder
  @Getter
  @Setter
  public static class Response {

    private int projectId;
    private int userId;
    private String title;
    private String content;
    private String projectOnOffline;
    private String place;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;
    private LocalDate dueDate;
    private int recruitmentNum;
    private List<RecruitmentDto> recruitmentDtos;

    // recruitmentDtos 는 Controller 에서 따로 넣어줌
    public static DeleteProject.Response fromEntity(ProjectDto projectDto){
      return Response.builder()
              .projectId(projectDto.getProjectId())
              .userId(projectDto.getUserId())
              .title(projectDto.getTitle())
              .content(projectDto.getContent())
              .projectOnOffline(projectDto.getProjectOnOffline().toString())
              .place(projectDto.getPlace())
              .createdTime(projectDto.getCreatedTime())
              .updatedTime(projectDto.getUpdatedTime())
              .deletedTime(projectDto.getDeletedTime())
              .dueDate(projectDto.getDueDate())
              .recruitmentNum(projectDto.getRecruitmentNum())
              .build();
    }

  }

}

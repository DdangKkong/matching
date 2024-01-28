package zerobase.matching.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.domain.Department;
import zerobase.matching.project.recruitment.dto.RecruitmentDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateProject {

  // recruitmentNum 은 수정 불가
  @Getter
  public static class Request {

    @NotNull
    private int userId;

    @NotNull
    private int projectId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String projectOnOffline;

    @NotNull
    private String place;

    @NotNull
    private LocalDate dueDate;

    private Department departmentOne;

    private int totalNumOne;

    private Department departmentTwo;

    private int totalNumTwo;

    private Department departmentThr;

    private int totalNumThr;

  }

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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private LocalDate dueDate;
    private int recruitmentNum;
    private List<RecruitmentDto> recruitmentDtos;


    // recruitmentDtos 는 Controller 에서 따로 넣어줌
    public static UpdateProject.Response fromEntity(ProjectDto projectDto){
      return Response.builder()
              .projectId(projectDto.getProjectId())
              .userId(projectDto.getUserId())
              .title(projectDto.getTitle())
              .content(projectDto.getContent())
              .projectOnOffline(projectDto.getProjectOnOffline().toString())
              .place(projectDto.getPlace())
              .createTime(projectDto.getCreateTime())
              .updateTime(projectDto.getUpdateTime())
              .deleteTime(projectDto.getDeleteTime())
              .dueDate(projectDto.getDueDate())
              .recruitmentNum(projectDto.getRecruitmentNum())
              .build();
    }

  }

}

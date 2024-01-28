package zerobase.matching.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.recruitment.dto.RecruitmentDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReadProject {


  @Builder
  @Getter // 이거 없으면 403 에러 뜸 ( 클라이언트가 요청한 Type 으로 응답을 내려줄 수 없다 )
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
    public static ReadProject.Response fromEntity(ProjectDto projectDto){
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

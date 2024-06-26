package zerobase.matching.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.recruitment.domain.Recruitment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProjectDto {
  // 구인 글 id
  private int projectId;

  // 회원 id
  private int userId;

  // 제목
  private String title;

  // 내용
  private String content;

  // 모임 형태
  private String projectOnOffline;

  // 모임 장소
  private String place;

  // 작성 일시
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdTime;

  // 수정 일시
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime updatedTime;

  // 삭제 일시
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime deletedTime;

  // 구인 마감 날짜
  private LocalDate dueDate;

  // 모집군의 갯수
  private int recruitmentNum;

  // 모집현황
  private List<Recruitment> recruitmentList;



  public static ProjectDto fromEntity(Project projects, List<Recruitment> recruitmentList) {
    return ProjectDto.builder()
        .projectId(projects.getProjectId())
        .userId(projects.getUser().getUserId())
        .title(projects.getTitle())
        .content(projects.getContent())
        .projectOnOffline(projects.getProjectOnOffline().toString())
        .place(projects.getPlace())
        .createdTime(projects.getCreatedTime())
        .updatedTime(projects.getUpdatedTime())
        .deletedTime(projects.getDeletedTime())
        .dueDate(projects.getDueDate())
        .recruitmentNum(projects.getRecruitmentNum())
        .recruitmentList(recruitmentList)
        .build();
  }

}

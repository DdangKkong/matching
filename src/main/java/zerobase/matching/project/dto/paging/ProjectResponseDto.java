package zerobase.matching.project.dto.paging;

import lombok.*;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.domain.ProjectOnOffline;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProjectResponseDto {
  // 구인 글 id
  private int projectId;

  // 회원 id
  private int userId;

  // 제목
  private String title;

  // 내용
  private String content;

  // 모임 형태
  private ProjectOnOffline projectOnOffline;

  // 모임 장소
  private String place;

  // 모집 인원
  private int numberOfRecruit;

  // 현재 인원
  private int currentRecruit;

  // 작성 일시
  private LocalDateTime createTime;

  // 수정 일시
  private LocalDateTime updateTime;

  // 구인 마감 날짜
  private LocalDate dueDate;

  @Builder
  @Getter
  public static class Response {

    private int projectId;
    private int userId;
    private String title;
    private String content;
    private String projectOnOffline;
    private String place;
    private int numberOfRecruit;
    private LocalDateTime createTime;
    private LocalDate dueDate;
    private int recruitmentNum;

    public static ProjectResponseDto.Response fromEntity(Project project) {
      return ProjectResponseDto.Response.builder()
          .projectId(project.getProjectId())
          .userId(project.getUser().getUserId())
          .title(project.getTitle())
          .content(project.getContent())
          .projectOnOffline(project.getProjectOnOffline().toString())
          .place(project.getPlace())
          .createTime(LocalDateTime.now())
          .dueDate(project.getDueDate())
          .recruitmentNum(project.getRecruitmentNum())
          .build();
    }

  }
}

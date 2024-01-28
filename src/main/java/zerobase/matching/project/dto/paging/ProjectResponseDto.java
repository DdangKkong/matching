package zerobase.matching.project.dto.paging;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.domain.ProjectOnOffline;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProjectResponseDto {
  // 구인 글 id
  private long projectId;

  // 회원 id
  private long userId;

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

    private long projectId;
    private long userId;
    private String title;
    private String content;
    private String projectOnOffline;
    private String place;
    private int numberOfRecruit;
    private LocalDateTime createTime;
    private LocalDate dueDate;

    public static ProjectResponseDto.Response fromEntity(Project project) {
      return ProjectResponseDto.Response.builder()
          .projectId(project.getProjectId())
          .userId(project.getUser().getUserId())
          .title(project.getTitle())
          .content(project.getContent())
          .projectOnOffline(project.getProjectOnOffline().toString())
          .place(project.getPlace())
          .numberOfRecruit(project.getNumberOfRecruit())
          .createTime(LocalDateTime.now())
          .dueDate(project.getDueDate())
          .build();
    }

  }
}

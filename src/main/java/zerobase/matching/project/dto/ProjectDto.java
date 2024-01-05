package zerobase.matching.project.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.domain.ProjectOnOffline;
import zerobase.matching.project.domain.Project;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProjectDto {
  // 구인 글 id
  private BigInteger projectId;

  // 회원 id
  private BigInteger userId;

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

  public static ProjectDto fromEntity(Project projects) {
    return ProjectDto.builder()
        .projectId(projects.getProjectId())
        .userId(projects.getUser().getUserId())
        .title(projects.getTitle())
        .content(projects.getContent())
        .projectOnOffline(projects.getProjectOnOffline())
        .place(projects.getPlace())
        .numberOfRecruit(projects.getNumberOfRecruit())
        .currentRecruit(projects.getCurrentRecruit())
        .createTime(projects.getCreateTime())
        .updateTime(projects.getUpdateTime())
        .dueDate(projects.getDueDate())
        .build();
  }

}
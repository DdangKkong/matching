package zerobase.matching.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.user.persist.entity.UserEntity;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECT")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PROJECT_ID")
  private long projectId;

  // 제목
  @Column(name = "TITLE")
  private String title;

  // 내용
  @Column(name = "CONTENT")
  private String content;

  // 모임 형태
  @Column(name = "PROJECT_ON_OFFLINE")
  @Enumerated(EnumType.STRING) // 값을 그대로 database 에 저장 { 없으면 숫자(상수)로 저장됨 }
  private ProjectOnOffline projectOnOffline;

  // 모임 장소
  @Column(name = "PLACE")
  private String place;

  // 모집 인원
  @Column(name = "NUMBER_OF_RECRUIT")
  private int numberOfRecruit;

  // 현재 인원
  @Column(name = "CURRENT_RECRUIT")
  private int currentRecruit;

  // 작성 일시
  @Column(name = "CREATE_TIME")
  private LocalDateTime createTime;

  // 수정 일시
  @Column(name = "UPDATE_TIME")
  private LocalDateTime updateTime;

  // 구인 마감 날짜
  @Column(name = "DUE_DATE")
  private LocalDate dueDate;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

  public static Project setEntity(Project project, String title, String content,
      ProjectOnOffline projectOnOffline, String place, int numberOfRecruit, LocalDate dueDate) {

    project.setTitle(title);
    project.setContent(content);
    project.setProjectOnOffline(projectOnOffline);
    project.setPlace(place);
    project.setNumberOfRecruit(numberOfRecruit);
    project.setDueDate(dueDate);
    project.setUpdateTime(LocalDateTime.now());

    return project;
  }

  public static Project deleteEntity(Project project) {
    // 구인 글 삭제시, 댓글을 보지 못하기에 title 제외한 요소들 초기화로 대체
    project.setContent("deleted");
    project.setProjectOnOffline(ProjectOnOffline.deleted);
    project.setPlace("deleted");
    project.setNumberOfRecruit(0);
    project.setCurrentRecruit(0);
    project.setDueDate(LocalDate.MIN);
    project.setCreateTime(LocalDateTime.MIN);
    project.setUpdateTime(LocalDateTime.MIN);

    return project;
  }

}


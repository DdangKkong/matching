package zerobase.matching.project.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.user.persist.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
  private int projectId;

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

  // 작성 일시
  @Column(name = "CREATED_TIME")
  private LocalDateTime createdTime;

  // 수정 일시
  @Column(name = "UPDATED_TIME")
  private LocalDateTime updatedTime;

  // 삭제 일시
  @Column(name = "DELETED_TIME")
  private LocalDateTime deletedTime;

  // 구인 마감 날짜
  @Column(name = "DUE_DATE")
  private LocalDate dueDate;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

  // 모집군의 갯수
  @Column(name = "RECRUITMENT_NUM")
  private int recruitmentNum;



  public static Project setEntity(Project project, String title, String content,
      String projectOnOffline, String place, LocalDate dueDate) {

    project.setTitle(title);
    project.setContent(content);
    project.setProjectOnOffline(ProjectOnOffline.valueOf(projectOnOffline));
    project.setPlace(place);
    project.setDueDate(dueDate);
    project.setUpdatedTime(LocalDateTime.now());

    return project;
  }

  public static Project deleteEntity(Project project) {
    // 구인 글 삭제시, 댓글을 보지 못하기에 title 제외한 요소들 초기화로 대체
    project.setContent("deleted");
    project.setProjectOnOffline(ProjectOnOffline.deleted);
//    project.setDepartment(Department.deleted);
    project.setPlace("deleted");
    project.setDueDate(null);
    project.setCreatedTime(null);
    project.setUpdatedTime(null);
    project.setDeletedTime(LocalDateTime.now());

    return project;
  }

}


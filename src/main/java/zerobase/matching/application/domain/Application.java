package zerobase.matching.application.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.project.domain.Department;
import zerobase.matching.project.domain.Project;
import zerobase.matching.user.persist.entity.UserEntity;

import java.time.LocalDateTime;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPLICATION")
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "APPLICATION_ID")
  private int applicationId;

  // 제목
  @Column(name = "TITLE")
  private String title;

  // 자기 소개
  @Column(name = "PROMOTION")
  private String promotion;

  // 참여 이유
  @Column(name = "DETAILS")
  private String details;

  // 신청서 작성 일시
  @Column(name = "CREATED_AT")
  private LocalDateTime createdTime;

  // 신청서 수정 일시
  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedTime;

  // 신청서 지원 일시
  @Column(name = "APPLY_AT")
  private LocalDateTime applyTime;

  // 신청서 삭제 일시
  @Column(name = "DELETED_AT")
  private LocalDateTime deletedTime;

  @Column(name = "DEPARTMENT")
  @Enumerated(EnumType.STRING) // 값을 그대로 database 에 저장 { 없으면 숫자(상수)로 저장됨 }
  private Department department;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private Project project;


}

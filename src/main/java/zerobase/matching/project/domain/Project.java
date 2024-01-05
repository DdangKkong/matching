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
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger projectId;

  // 제목
  @Column(name = "title")
  private String title;

  // 내용
  @Column(name = "content")
  private String content;

  // 모임 형태
  @Column(name = "project_on_offline")
  @Enumerated(EnumType.STRING) // 값을 그대로 database 에 저장 { 없으면 숫자(상수)로 저장됨 }
  private ProjectOnOffline projectOnOffline;

  // 모임 장소
  @Column(name = "place")
  private String place;

  // 모집 인원
  @Column(name = "number_of_recruit")
  private int numberOfRecruit;

  // 현재 인원
  @Column(name = "current_recruit")
  private int currentRecruit;

  // 작성 일시
  @Column(name = "create_time")
  private LocalDateTime createTime;

  // 수정 일시
  @Column(name = "update_time")
  private LocalDateTime updateTime;

  // 구인 마감 날짜
  @Column(name = "due_date")
  private LocalDate dueDate;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}

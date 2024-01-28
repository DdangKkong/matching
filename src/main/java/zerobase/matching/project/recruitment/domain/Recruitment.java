package zerobase.matching.project.recruitment.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.project.domain.Department;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RECRUITMENT") // 모집 현황
public class Recruitment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "RECRUITMENT_ID")
  private int recruitmentId;

  // 역할군
  @Column(name = "DEPARTMENT")
  @Enumerated(EnumType.STRING) // 값을 그대로 database 에 저장 { 없으면 숫자(상수)로 저장됨 }
  private Department department;

  // 총 모집 인원수
  @Column(name = "TOTAL_NUM")
  private int totalNum;

  // 현재 모집 인원수
  @Column(name = "CURRENT_NUM")
  private int currentNum;

}

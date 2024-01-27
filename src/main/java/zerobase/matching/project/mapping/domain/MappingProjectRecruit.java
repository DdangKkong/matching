package zerobase.matching.project.mapping.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.recruitment.domain.Recruitment;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MAPPING_PROJECT_RECRUIT")
public class MappingProjectRecruit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MAPPING_ID")
  private int mappingId;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "RECRUITMENT_ID")
  private Recruitment recruitment;

}


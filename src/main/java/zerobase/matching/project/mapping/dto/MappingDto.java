package zerobase.matching.project.mapping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.mapping.domain.MappingProjectRecruit;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MappingDto {

  // 매핑 id
  private int mappingId;

  // 구인 글 id
  private int projectId;

  // 모집현황 id
  private int recruitmentId;



  public static MappingDto fromEntity(MappingProjectRecruit mappingProjectRecruit) {
    return MappingDto.builder()
                    .mappingId(mappingProjectRecruit.getMappingId())
                    .projectId(mappingProjectRecruit.getProject().getProjectId())
                    .recruitmentId(mappingProjectRecruit.getRecruitment().getRecruitmentId())
                    .build();
  }

}

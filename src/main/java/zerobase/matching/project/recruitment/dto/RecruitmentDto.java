package zerobase.matching.project.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.domain.Department;
import zerobase.matching.project.recruitment.domain.Recruitment;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecruitmentDto {

  // 모집 현황 id
  private int recruitmentId;

  // 총 모집 인원수
  private int totalNum;

  // 현재 모집 인원수
  private int currentNum;

  private Department department;


  public static RecruitmentDto fromEntity(Recruitment recruitment) {
    return RecruitmentDto.builder()
        .recruitmentId(recruitment.getRecruitmentId())
        .department(recruitment.getDepartment())
        .totalNum(recruitment.getTotalNum())
        .currentNum(recruitment.getCurrentNum())
        .build();
  }

}

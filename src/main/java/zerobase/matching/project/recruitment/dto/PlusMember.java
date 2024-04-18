package zerobase.matching.project.recruitment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import zerobase.matching.project.domain.Department;

public class PlusMember {

  @Getter
  @Builder
  @AllArgsConstructor
  public static class Request {

    @NotNull
    private int projectId;
    @NotNull
    private Department department;

  }

  @Builder
  @Getter
  public static class Response {

    private int recruitmentId;
    private String department;
    private int totalNum;
    private int currentNum;

    public static Response fromEntity(RecruitmentDto recruitmentDto){
      return Response.builder()
          .recruitmentId(recruitmentDto.getRecruitmentId())
          .department(recruitmentDto.getDepartment().toString())
          .totalNum(recruitmentDto.getTotalNum())
          .currentNum(recruitmentDto.getCurrentNum())
          .build();
    }

  }

}

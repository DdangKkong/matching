package zerobase.matching.project.recruitment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import zerobase.matching.project.domain.Department;

public class RequestRecruitmentThr {

  @Getter
  @Builder
  @AllArgsConstructor
  public static class Request {

    @NotNull
    private Department departmentOne;
    @NotNull
    private int totalNumOne;
    @NotNull
    private Department departmentTwo;
    @NotNull
    private int totalNumTwo;
    @NotNull
    private Department departmentThr;
    @NotNull
    private int totalNumThr;
  }

}

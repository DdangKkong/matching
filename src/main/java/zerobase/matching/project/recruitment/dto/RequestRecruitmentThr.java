package zerobase.matching.project.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import zerobase.matching.project.domain.Department;

public class RequestRecruitmentThr {

  @Getter
  @Builder
  @AllArgsConstructor
  public static class Request {

    private Department departmentOne;

    private int totalNumOne;

    private Department departmentTwo;

    private int totalNumTwo;

    private Department departmentThr;

    private int totalNumThr;
  }

}

package zerobase.matching.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class SendApplication {

  @Getter
  public static class Request {

    @NotNull
    private int userId;
    @NotNull
    private int applicationId;

  }

  @Builder
  @Getter
  public static class Response {

    private int applicationId;
    private int userId;
    private int projectId;
    private String department;
    private String title;
    private String promotion;
    private String details;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
    private LocalDateTime applyTime;

    public static SendApplication.Response fromEntity(ApplicationDto applicationDto) {
      return SendApplication.Response.builder()
          .applicationId(applicationDto.getApplicationId())
          .userId(applicationDto.getUserId())
          .projectId(applicationDto.getProjectId())
          .department(applicationDto.getDepartment().toString())
          .title(applicationDto.getTitle())
          .promotion(applicationDto.getPromotion())
          .details(applicationDto.getDetails())
          .createTime(applicationDto.getCreateTime())
          .updateTime(applicationDto.getUpdateTime())
          .deleteTime(applicationDto.getDeleteTime())
          .applyTime(applicationDto.getApplyTime())
          .build();
    }

  }

}

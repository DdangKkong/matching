package zerobase.matching.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UpdateApplication {

  @Getter
  public static class Request {

    @NotNull
    private int userId;
    @NotBlank
    private String department;
    @NotBlank
    private String title;
    @NotBlank
    private String promotion;
    @NotBlank
    private String details;

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
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;
    private LocalDateTime applyTime;

    public static UpdateApplication.Response fromEntity(ApplicationDto applicationDto) {
      return UpdateApplication.Response.builder()
          .applicationId(applicationDto.getApplicationId())
          .userId(applicationDto.getUserId())
          .projectId(applicationDto.getProjectId())
          .department(applicationDto.getDepartment().toString())
          .title(applicationDto.getTitle())
          .promotion(applicationDto.getPromotion())
          .details(applicationDto.getDetails())
          .createdTime(applicationDto.getCreatedTime())
          .updatedTime(applicationDto.getUpdatedTime())
          .deletedTime(applicationDto.getDeletedTime())
          .applyTime(applicationDto.getApplyTime())
          .build();
    }

  }

}

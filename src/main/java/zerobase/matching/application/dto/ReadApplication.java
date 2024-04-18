package zerobase.matching.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class ReadApplication {

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

    public static ReadApplication.Response fromEntity(ApplicationDto applicationDto) {
      return ReadApplication.Response.builder()
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

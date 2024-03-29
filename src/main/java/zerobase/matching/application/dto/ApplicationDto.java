package zerobase.matching.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.application.domain.Application;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApplicationDto {

  // 신청서 id
  private int applicationId;

  // 회원 id
  private int userId;

  // 구인 글 id
  private int projectId;

  // 역할군
  private String department;

  // 제목
  private String title;

  // 자기 소개
  private String promotion;

  // 참여 이유
  private String details;

  // 신청서 작성 일시
  private LocalDateTime createdTime;

  // 신청서 수정 일시
  private LocalDateTime updatedTime;

  // 신청서 지원 일시
  private LocalDateTime applyTime;

  // 신청서 삭제 일시
  private LocalDateTime deletedTime;

  public static ApplicationDto fromEntity(Application application) {
    return ApplicationDto.builder()
        .applicationId(application.getApplicationId())
        .userId(application.getUser().getUserId())
        .department(application.getDepartment().toString())
        .title(application.getTitle())
        .promotion(application.getPromotion())
        .details(application.getDetails())
        .createdTime(application.getCreatedTime())
        .updatedTime(application.getUpdatedTime())
        .applyTime(application.getApplyTime())
        .deletedTime(application.getDeletedTime())
        .build();
  }

  public static ApplicationDto fromEntityWithProjectId(Application application) {
    return ApplicationDto.builder()
            .applicationId(application.getApplicationId())
            .userId(application.getUser().getUserId())
            .projectId(application.getProject().getProjectId())
            .department(application.getDepartment().toString())
            .title(application.getTitle())
            .promotion(application.getPromotion())
            .details(application.getDetails())
            .createdTime(application.getCreatedTime())
            .updatedTime(application.getUpdatedTime())
            .applyTime(application.getApplyTime())
            .deletedTime(application.getDeletedTime())
            .build();
  }

}

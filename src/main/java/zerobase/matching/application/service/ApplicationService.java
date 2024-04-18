package zerobase.matching.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.matching.application.domain.Application;
import zerobase.matching.application.dto.*;
import zerobase.matching.application.dto.paging.ApplicationPagingResponse;
import zerobase.matching.application.repository.ApplicationRepository;
import zerobase.matching.project.domain.Department;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.exception.CustomException;
import zerobase.matching.user.exception.ErrorCode;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final ApplicationRepository applicationRepository;

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  // 신청서 작성
  public ApplicationDto createApplication(CreateApplication.Request request) {
    UserEntity user = getUser(request.getUserId());

    return ApplicationDto.fromEntity(applicationRepository.save(
            Application.builder()
            .user(user)
            .department(Department.valueOf(request.getDepartment()))
            .title(request.getTitle())
            .promotion(request.getPromotion())
            .details(request.getDetails())
            .createdTime(LocalDateTime.now())
            .build()
    ));
  }

  // 신청서 제출
  @Transactional
  public ApplicationDto sendApplication(int projectId, SendApplication.Request request) {
    Project project = getProject(projectId);
    Application application = getApplication(request.getApplicationId());
    UserEntity user = getUser(request.getUserId());
    int writerId = application.getUser().getUserId();

    // 작성자가 아닌 경우 신청서 제출 불가
    if (!Objects.equals(writerId, request.getUserId())) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    application.setApplyTime(LocalDateTime.now());
    application.setProject(project);

    return ApplicationDto.fromEntityWithProjectId(applicationRepository.save(application));
  }

  // 신청서 회수 ( Project 를 null 값으로 )
  @Transactional
  public ApplicationDto retrieveApplication(RetrieveApplication.Request request) {
    Application application = getApplication(request.getApplicationId());
    UserEntity user = getUser(request.getUserId());
    int writerId = application.getUser().getUserId();

    // 작성자가 아닌 경우 신청서 회수 불가
    if (!Objects.equals(writerId, request.getUserId())) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    int sendProjectId = application.getProject().getProjectId();

    // 신청서 제출 여부 확인 ( projectId 일치 여부 확인 )
    if (!Objects.equals(sendProjectId, request.getProjectId())) {
      throw new CustomException(ErrorCode.APPLICATIONID_INVALID);
    }

    application.setProject(null);
    application.setApplyTime(null);

    return ApplicationDto.fromEntity(application);
  }

  // 신청서 읽기
  public ApplicationDto readApplication(int applicationId, int userId) {
    Application application = getApplication(applicationId);
    UserEntity user = getUser(userId);
    int writerId = application.getUser().getUserId();

    // 작성자가 아닌 경우 신청서 읽기 불가
    if (!Objects.equals(writerId, userId)) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    if (application.getApplyTime() == null) {return ApplicationDto.fromEntity(application);}
    else { return ApplicationDto.fromEntityWithProjectId(application);}

  }

  // 신청서 수정
  @Transactional
  public ApplicationDto updateApplication(int applicationId, UpdateApplication.Request request) {
    Application application = getApplication(applicationId);
    UserEntity user = getUser(request.getUserId());
    int writerId = application.getUser().getUserId();

    // 작성자가 아닌 경우 신청서 수정 불가
    if (!Objects.equals(writerId, request.getUserId())) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    application.setDepartment(Department.valueOf(request.getDepartment()));
    application.setTitle(request.getTitle());
    application.setPromotion(request.getPromotion());
    application.setDetails(request.getDetails());
    application.setUpdatedTime(LocalDateTime.now());

    if (application.getApplyTime() == null) {return ApplicationDto.fromEntity(applicationRepository.save(application));}
    else { return ApplicationDto.fromEntityWithProjectId(applicationRepository.save(application));}

  }

  // 신청서 삭제
  @Transactional
  public void deleteApplication(int applicationId, int userId) {
    Application application = getApplication(applicationId);
    UserEntity user = getUser(userId);
    int writerId = application.getUser().getUserId();

    // 작성자가 아닌 경우 신청서 삭제 불가
    if (!Objects.equals(writerId, userId)) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    // 신청서를 제출한 경우 삭제 불가
    if (application.getApplyTime() != null) {
      throw new CustomException(ErrorCode.APPLICATIONID_INVALID);
    }

    applicationRepository.delete(application);
  }

  // 자신의 신청서 리스트업 ( page 요소 추가 )
  public ApplicationPagingResponse pagingApplications(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Application> ApplicationPage = applicationRepository.findAll(pageable);

    List<Application> ApplicationList = ApplicationPage.getContent();
    List<ApplicationDto> content = ApplicationList.stream().map(
            Application -> ApplicationDto.fromEntity(Application)).collect(Collectors.toList()); // projectId 제외

    return ApplicationPagingResponse.builder()
            .content(content)
            .pageNo(page)
            .pageSize(size)
            .totalElements(ApplicationPage.getTotalElements())
            .totalPages(ApplicationPage.getTotalPages())
            .build();
  }

  private UserEntity getUser(int userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USERID_INVALID));
  }

  private Project getProject(int projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECTID_INVALID));
  }

  private Application getApplication(int applicationId) {
    return applicationRepository.findById(applicationId)
            .orElseThrow(() -> new CustomException(ErrorCode.APPLICATIONID_INVALID));
  }

}






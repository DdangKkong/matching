package zerobase.matching.project.service;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.domain.ProjectOnOffline;
import zerobase.matching.project.dto.ProjectDto;
import zerobase.matching.project.dto.paging.ProjectPagingResponse;
import zerobase.matching.project.dto.paging.ProjectResponseDto;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  // 프로젝트 구인 글 작성
  public ProjectDto createProject(long userId, String title, String content,
      ProjectOnOffline projectOnOffline, String place, int numberOfRecruit,
      @NotNull LocalDate dueDate){
    UserEntity user = getUser(userId);

    return ProjectDto.fromEntity(projectRepository.save(
        Project.builder()
            .title(title).content(content).projectOnOffline(projectOnOffline)
            .place(place).numberOfRecruit(numberOfRecruit).user(user).dueDate(dueDate)
            .createTime(LocalDateTime.now())
            .build()
    ));
  }

  // 프로젝트 구인 글 읽기
  public ProjectDto readProject(long projectId) {
    Project project = getProject(projectId);

    return ProjectDto.fromEntity(project);
  }

  // 프로젝트 구인 글 수정
  public ProjectDto updateProject(
      long userId, long projectId, String title, String content,
      ProjectOnOffline projectOnOffline, String place, int numberOfRecruit, LocalDate dueDate) {
    UserEntity user = getUser(userId);
    Project project = getProject(projectId);
    long writerId = project.getUser().getUserId();

    // 작성자가 아닌 경우 구인 글 수정 불가
    if (!Objects.equals(writerId, userId)) {
      throw new RuntimeException(
          user.getNickname() + "님은 해당 구인 글의 작성자가 아닙니다.");
    }

    // 수정된 모집인원이 현재 모집된 인원보다 적은경우 에러
    if (numberOfRecruit < project.getCurrentRecruit()) {
      throw new RuntimeException("모집인원이 모집된 인원에 비해 너무 적습니다");
    }

    Project newProject = Project.setEntity(project, title, content,
        projectOnOffline, place, numberOfRecruit, dueDate);

    projectRepository.save(newProject);

    return ProjectDto.fromEntity(newProject);
  }

  // 프로젝트 구인 글 삭제
  public ProjectDto deleteProject(long userId, long projectId) {
    UserEntity user = getUser(userId);
    Project project = getProject(projectId);
    long writerId = project.getUser().getUserId();

    // 작성자가 아닌 경우 구인 글 수정 불가
    if (!Objects.equals(writerId, userId)) {
      throw new RuntimeException(
          user.getNickname() +
          "님은 해당 구인 글의 작성자가 아닙니다.");
    }

    Project deletedProject = Project.deleteEntity(project);
    projectRepository.save(deletedProject);

    return ProjectDto.fromEntity(deletedProject);
  }

  // 프로젝트 모든 구인 글 조회 ( 메인 화면, page 요소 추가 )
  public ProjectPagingResponse pagingProjects(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.findAll(pageable);

    List<Project> projectList = projectPage.getContent();
    List<ProjectResponseDto.Response> content = projectList.stream().map(
        Project -> ProjectResponseDto.Response.fromEntity(Project)).collect(Collectors.toList());

    return ProjectPagingResponse.builder()
        .content(content)
        .pageNo(page)
        .pageSize(size)
        .totalElements(projectPage.getTotalElements())
        .totalPages(projectPage.getTotalPages())
        .build();
  }

  private UserEntity getUser(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("회원 권한이 없습니다."));
  }

  private Project getProject(long projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("프로젝트 구인 글이 없습니다."));
  }

}

package zerobase.matching.service;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import zerobase.matching.domain.ProjectOnOffline;
import zerobase.matching.domain.Project;
import zerobase.matching.domain.User;
import zerobase.matching.dto.ProjectDto;
import zerobase.matching.repository.ProjectRepository;
import zerobase.matching.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  // 프로젝트 구인 글 작성
  public ProjectDto createProject(BigInteger userId, String title, String content,
      ProjectOnOffline projectOnOffline, String place, int numberOfRecruit,
      @NotNull LocalDate dueDate){
    User user = getUser(userId);

    return ProjectDto.fromEntity(projectRepository.save(
        Project.builder()
            .title(title).content(content).projectOnOffline(projectOnOffline)
            .place(place).numberOfRecruit(numberOfRecruit).user(user).dueDate(dueDate)
            .build()
    ));
  }

  // 프로젝트 구인 글 읽기
  public ProjectDto readProject(BigInteger projectId) {
    Project project = getProject(projectId);

    return ProjectDto.fromEntity(project);
  }

  // 프로젝트 구인 글 수정
  public ProjectDto updateProject(
      BigInteger userId, BigInteger projectId, String title, String content,
      ProjectOnOffline projectOnOffline, String place, int numberOfRecruit, LocalDate dueDate) {
    User user = getUser(userId);
    Project project = getProject(projectId);
    BigInteger writerId = project.getUser().getUserId();

    // 작성자가 아닌 경우 구인 글 수정 불가
    if (!Objects.equals(writerId, userId)) {
      throw new RuntimeException(
//          user.getNickName() +
              "님은 해당 구인 글의 작성자가 아닙니다.");
    }

    // 수정된 모집인원이 현재 인원보다 적은경우 에러


    project.setTitle(title);
    project.setContent(content);
    project.setProjectOnOffline(projectOnOffline);
    project.setPlace(place);
    project.setNumberOfRecruit(numberOfRecruit);
    project.setDueDate(dueDate);
    project.setUpdateTime(LocalDateTime.now());

    return ProjectDto.fromEntity(project);
  }

  // 프로젝트 구인 글 삭제
  public ProjectDto deleteProject(BigInteger userId, BigInteger projectId) {
    User user = getUser(userId);
    Project project = getProject(projectId);
    BigInteger writerId = project.getUser().getUserId();

    // 작성자가 아닌 경우 구인 글 수정 불가
    if (!Objects.equals(writerId, userId)) {
      throw new RuntimeException(
//          user.getNickName() +
          "님은 해당 구인 글의 작성자가 아닙니다.");
    }

    // 구인 글 삭제시, 댓글을 보지 못하기에 title 제외한 요소들 초기화로 대체
    project.setContent("deleted");
    project.setProjectOnOffline(ProjectOnOffline.deleted);
    project.setPlace("deleted");
    project.setNumberOfRecruit(0);
    project.setCurrentRecruit(0);
    project.setDueDate(LocalDate.MIN);
    project.setCreateTime(LocalDateTime.MIN);
    project.setUpdateTime(LocalDateTime.MIN);

    return ProjectDto.fromEntity(project);
  }

  // 프로젝트 모든 구인 글 조회 ( 메인 화면, 구인 신청 가능한 글만, page 요소 추가 ) - 추후 구현 예정
//  public Page<Project> findProjects(int page, int size) {
//    List<Project> projects = projectRepository.findAllByDueDateBefore(LocalDate.now());
//
//    return listToPage(projects, page, size);
//  }
//
//  private Page<Project> listToPage(List<Project> projects, int page, int size) {
//    PageRequest pageRequest = PageRequest.of(page-1, size);
//    int start = (int)pageRequest.getOffset();
//    int end = Math.min((start + pageRequest.getPageSize()), projects.size());
//    List<Project> subList = start>=end?new ArrayList<>():projects.subList(start,end);
//    return new PageImpl<>(subList, pageRequest, (long) projects.size());
//  }

  private User getUser(BigInteger userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("회원 권한이 없습니다."));
  }

  private Project getProject(BigInteger projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("프로젝트 구인 글이 없습니다."));
  }

}

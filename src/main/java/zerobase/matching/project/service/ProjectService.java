package zerobase.matching.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.dto.CreateProject;
import zerobase.matching.project.dto.ProjectDto;
import zerobase.matching.project.dto.UpdateProject;
import zerobase.matching.project.dto.paging.ProjectPagingResponse;
import zerobase.matching.project.dto.paging.ProjectResponseDto;
import zerobase.matching.project.mapping.domain.MappingProjectRecruit;
import zerobase.matching.project.mapping.repository.MappingRepository;
import zerobase.matching.project.mapping.service.MappingService;
import zerobase.matching.project.recruitment.domain.Recruitment;
import zerobase.matching.project.recruitment.dto.RequestRecruitmentOne;
import zerobase.matching.project.recruitment.dto.RequestRecruitmentThr;
import zerobase.matching.project.recruitment.dto.RequestRecruitmentTwo;
import zerobase.matching.project.recruitment.repository.RecruitmentRepository;
import zerobase.matching.project.recruitment.service.RecruitmentService;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  private final RecruitmentRepository recruitmentRepository;

  private final MappingRepository mappingRepository;

  private final RecruitmentService recruitmentService;

  private final MappingService mappingService;

  // 프로젝트 구인 글 작성
  public ProjectDto createProject(CreateProject.Request request){
    UserEntity user = getUser(request.getUserId());

    // dueDate 가 이미 지난 날짜라면 에러발생




    Project project = projectRepository.save(
            Project.builder()
                    .title(request.getTitle()).content(request.getContent()).projectOnOffline(request.getProjectOnOffline())
                    .place(request.getPlace()).user(user).dueDate(request.getDueDate())
                    .createdTime(LocalDateTime.now()).recruitmentNum(request.getRecruitmentNum())
                    .build());

    int projectId = project.getProjectId();

    List<Recruitment> recruitmentList = new ArrayList<>();
    int recruitmentId1 = -1;
    int recruitmentId2 = -1;
    int recruitmentId3 = -1;

    // 모집군의 갯수에 따라 List 에 들어갈 내용이 달라짐
    if (request.getRecruitmentNum() == 1) {
      RequestRecruitmentOne.Request request1 = new RequestRecruitmentOne.Request(
              request.getDepartmentOne(), request.getTotalNumOne());
      recruitmentList = recruitmentService.createRecruitmentOne(request1);
      recruitmentId1 = recruitmentList.get(0).getRecruitmentId();
    } else if (request.getRecruitmentNum() == 2) {
      RequestRecruitmentTwo.Request request2 = new RequestRecruitmentTwo.Request(
              request.getDepartmentOne(), request.getTotalNumOne(),
              request.getDepartmentTwo(), request.getTotalNumTwo());
      recruitmentList = recruitmentService.createRecruitmentTwo(request2);
      recruitmentId1 = recruitmentList.get(0).getRecruitmentId();
      recruitmentId2 = recruitmentList.get(1).getRecruitmentId();
    } else if (request.getRecruitmentNum() == 3) {
      RequestRecruitmentThr.Request request3 = new RequestRecruitmentThr.Request(
              request.getDepartmentOne(), request.getTotalNumOne(),
              request.getDepartmentTwo(), request.getTotalNumTwo(),
              request.getDepartmentThr(), request.getTotalNumThr());
      recruitmentList = recruitmentService.createRecruitmentThr(request3);
      recruitmentId1 = recruitmentList.get(0).getRecruitmentId();
      recruitmentId2 = recruitmentList.get(1).getRecruitmentId();
      recruitmentId3 = recruitmentList.get(2).getRecruitmentId();
    }

    // MappingProjectRecruit DB 관계 매핑 데이터 추가
    if (request.getRecruitmentNum() == 1) {
      mappingService.createMapping(projectId, recruitmentId1);
    } else if (request.getRecruitmentNum() == 2) {
      mappingService.createMapping(projectId, recruitmentId1);
      mappingService.createMapping(projectId, recruitmentId2);
    } else if (request.getRecruitmentNum() == 3) {
      mappingService.createMapping(projectId, recruitmentId1);
      mappingService.createMapping(projectId, recruitmentId2);
      mappingService.createMapping(projectId, recruitmentId3);
    }

    return ProjectDto.fromEntity(project, recruitmentList);
  }

  // 프로젝트 구인 글 읽기
  public ProjectDto readProject(int projectId) {
    Project project = getProject(projectId);
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(projectId);

    // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);

    return ProjectDto.fromEntity(project, recruitmentList);
  }

  // 프로젝트 구인 글 수정
  public ProjectDto updateProject(UpdateProject.Request request) {
    UserEntity user = getUser(request.getUserId());
    Project project = getProject(request.getProjectId());
    int writerId = project.getUser().getUserId();
    List<Recruitment> recruitmentList = new ArrayList<>();

    // 작성자가 아닌 경우 구인 글 수정 불가
    if (!Objects.equals(writerId, request.getUserId())) {
      throw new RuntimeException(
          user.getNickname() + "님은 해당 구인 글의 작성자가 아닙니다.");
    }

    // 모임 현황 갯수 기준으로 수정
    if (project.getRecruitmentNum() == 1) {
      recruitmentList = recruitmentService.updateRecruitmentOne(request);
    } else if (project.getRecruitmentNum() == 2) {
      recruitmentList = recruitmentService.updateRecruitmentTwo(request);
    } else if (project.getRecruitmentNum() == 3) {
      recruitmentList = recruitmentService.updateRecruitmentThr(request);
    }

    // 나머지 수정
    Project newProject = Project.setEntity(project, request.getTitle(), request.getContent(),
            request.getProjectOnOffline(), request.getPlace(), request.getDueDate());

    projectRepository.save(newProject);

    return ProjectDto.fromEntity(newProject, recruitmentList);
  }

  // 프로젝트 구인 글 삭제
  public ProjectDto deleteProject(int userId, int projectId) {
    UserEntity user = getUser(userId);
    Project project = getProject(projectId);
    int writerId = project.getUser().getUserId();

    // 작성자가 아닌 경우 구인 글 수정 불가
    if (!Objects.equals(writerId, userId)) {
      throw new RuntimeException(
          user.getNickname() +
          "님은 해당 구인 글의 작성자가 아닙니다.");
    }

    // 모집 현황 삭제
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(projectId);

      // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);
    int recruitmentNum = project.getRecruitmentNum();
    List<Recruitment> deleteRecruitmentList = new ArrayList<>();
    if (recruitmentNum == 1) {
      deleteRecruitmentList = recruitmentService.DeleteRecruitmentOne(recruitmentList, List);
    } else if (recruitmentNum == 2) {
      deleteRecruitmentList = recruitmentService.DeleteRecruitmentTwo(recruitmentList, List);
    } else if (recruitmentNum == 3) {
      deleteRecruitmentList = recruitmentService.DeleteRecruitmentThr(recruitmentList, List);
    }

    Project deletedProject = Project.deleteEntity(project);
    projectRepository.save(deletedProject);

    return ProjectDto.fromEntity(deletedProject, deleteRecruitmentList);
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

  private List<Recruitment> getRecruitmentList(Project project, List<MappingProjectRecruit> List) {
    List<Recruitment> recruitmentList = new ArrayList<>();
    if (project.getRecruitmentNum() == 1) {
      int recruitmentId1 = List.get(0).getRecruitment().getRecruitmentId();
      Recruitment recruitment1 = getRecruitment(recruitmentId1);
      recruitmentList.add(recruitment1);
    } else if (project.getRecruitmentNum() == 2) {
      int recruitmentId1 = List.get(0).getRecruitment().getRecruitmentId();
      int recruitmentId2 = List.get(1).getRecruitment().getRecruitmentId();
      Recruitment recruitment1 = getRecruitment(recruitmentId1);
      Recruitment recruitment2 = getRecruitment(recruitmentId2);
      recruitmentList.add(recruitment1);
      recruitmentList.add(recruitment2);
    } else if (project.getRecruitmentNum() == 3) {
      int recruitmentId1 = List.get(0).getRecruitment().getRecruitmentId();
      int recruitmentId2 = List.get(1).getRecruitment().getRecruitmentId();
      int recruitmentId3 = List.get(2).getRecruitment().getRecruitmentId();
      Recruitment recruitment1 = getRecruitment(recruitmentId1);
      Recruitment recruitment2 = getRecruitment(recruitmentId2);
      Recruitment recruitment3 = getRecruitment(recruitmentId3);
      recruitmentList.add(recruitment1);
      recruitmentList.add(recruitment2);
      recruitmentList.add(recruitment3);
    }

    return recruitmentList;
  }

  private UserEntity getUser(int userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("회원 권한이 없습니다."));
  }

  private Project getProject(int projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("프로젝트 구인 글이 없습니다."));
  }

  private Recruitment getRecruitment(int recruitmentId) {
    return recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new RuntimeException("모집 현황이 없습니다."));
  }

}

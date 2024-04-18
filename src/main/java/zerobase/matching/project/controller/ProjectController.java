package zerobase.matching.project.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.project.dto.CreateProject;
import zerobase.matching.project.dto.DeleteProject;
import zerobase.matching.project.dto.ProjectDto;
import zerobase.matching.project.dto.ReadProject;
import zerobase.matching.project.dto.UpdateProject;
import zerobase.matching.project.dto.paging.ProjectPagingResponse;
import zerobase.matching.project.recruitment.dto.RecruitmentDto;
import zerobase.matching.project.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProjectController {

  private final ProjectService projectService;

  // 프로젝트 구인 글 작성
  @PostMapping("/maching/projects")
  public ResponseEntity<CreateProject.Response> createProject(
      @RequestBody @Valid CreateProject.Request request
  ){
    ProjectDto projectDto = projectService.createProject(request);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(request.getRecruitmentNum(), projectDto);

    CreateProject.Response response = CreateProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);

    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 읽기
  @GetMapping("/maching/projects")
  public ResponseEntity<ReadProject.Response> readProject(
      @RequestParam(value = "projectId") int projectId
  ){
    ProjectDto projectDto = projectService.readProject(projectId);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(projectDto.getRecruitmentNum(), projectDto);

    ReadProject.Response response = ReadProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);

    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 수정
  @PutMapping("/maching/projects")
  public ResponseEntity<UpdateProject.Response> updateProject(
      @RequestBody @Valid UpdateProject.Request request
  ){
    ProjectDto projectDto = projectService.updateProject(request);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(projectDto.getRecruitmentNum(), projectDto);

    UpdateProject.Response response = UpdateProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);

    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 삭제
  @DeleteMapping("/maching/projects")
  public ResponseEntity<DeleteProject.Response> deleteProject(
      @RequestParam int projectId,
      @RequestParam int userId
  ){
    ProjectDto projectDto = projectService.deleteProject(userId, projectId);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(projectDto.getRecruitmentNum(), projectDto);

    DeleteProject.Response response = DeleteProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);
    return ResponseEntity.ok(response);
  }

  // 프로젝트 모든 구인 글 조회 ( 메인 화면, page 요소 추가 )
  @GetMapping("/maching/main")
  public ProjectPagingResponse pagingProjects(
      @RequestParam(value = "page", required = false, defaultValue = "1") @Positive int page,
      @RequestParam("size") @Positive int size) {

    return projectService.pagingProjects(page-1, size); // 우리가 생각하는 1 페이지는 page 값이 0일때 이다
  }


  // List<Recruitment> --> List<RecruitmentDto> 변환
  private List<RecruitmentDto> ListToList(int recruitNum, ProjectDto projectDto) {
    List<RecruitmentDto> recruitmentDtoList = new ArrayList<>();

    recruitmentDtoList = projectDto.getRecruitmentList().stream().map(
            Recruitment -> RecruitmentDto.fromEntity(Recruitment)).collect(Collectors.toList());

    return recruitmentDtoList;

  }

}

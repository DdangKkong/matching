package zerobase.matching.project.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.matching.project.dto.*;
import zerobase.matching.project.dto.paging.ProjectPagingResponse;
import zerobase.matching.project.recruitment.dto.RecruitmentDto;
import zerobase.matching.project.service.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class ProjectController {

  private final ProjectService projectService;

  // 프로젝트 구인 글 작성
  @PostMapping("/projects")
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
  @GetMapping("/projects")
  public ResponseEntity<ReadProject.Response> readProject(
      @RequestParam(value = "projectId") int projectId){
    ProjectDto projectDto = projectService.readProject(projectId);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(projectDto.getRecruitmentNum(), projectDto);

    ReadProject.Response response = ReadProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);

    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 수정
  @PutMapping("/projects")
  public ResponseEntity<UpdateProject.Response> updateProject(
      @RequestBody @Valid UpdateProject.Request request){
    ProjectDto projectDto = projectService.updateProject(request);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(projectDto.getRecruitmentNum(), projectDto);

    UpdateProject.Response response = UpdateProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);

    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 삭제
  @DeleteMapping("/projects")
  public ResponseEntity<DeleteProject.Response> deleteProject(@RequestParam int projectId,
    @RequestBody @Valid DeleteProject.Request request){
    ProjectDto projectDto = projectService.deleteProject(request.getUserId(), projectId);

    // List<Recruitment> --> List<RecruitmentDto> 변환
    List<RecruitmentDto> recruitmentDtoList = ListToList(projectDto.getRecruitmentNum(), projectDto);

    DeleteProject.Response response = DeleteProject.Response.fromEntity(projectDto);
    response.setRecruitmentDtos(recruitmentDtoList);
    return ResponseEntity.ok(response);
  }

  // 프로젝트 모든 구인 글 조회 ( 메인 화면, page 요소 추가 )
  @GetMapping("/projects/list")
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

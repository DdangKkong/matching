package zerobase.matching.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.domain.Project;
import zerobase.matching.dto.CreateProject;
import zerobase.matching.dto.DeleteProject;
//import zerobase.matching.dto.PageInfo;
import zerobase.matching.dto.ProjectDto;
import zerobase.matching.dto.ProjectResponseDto;
import zerobase.matching.dto.ReadProject;
//import zerobase.matching.dto.ResponseDto;
import zerobase.matching.dto.UpdateProject;
//import zerobase.matching.mapper.ProjectMapper;
import zerobase.matching.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class ProjectController {

  private final ProjectService projectService;
//  private final ProjectMapper mapper;

  // 프로젝트 구인 글 작성
  @PostMapping("/projects")
  public CreateProject.Response createProject(
      @RequestBody @Valid CreateProject.Request request
//      ,Authentication authentication
  ){
    return CreateProject.Response.fromEntity(
        projectService.createProject(
            request.getUserId(), request.getTitle(), request.getContent(),
            request.getProjectOnOffline(), request.getPlace(),
            request.getNumberOfRecruit(), request.getDueDate()
        )
    );
  }

  // 프로젝트 구인 글 읽기
  @GetMapping("/projects/{projectId}")
  public ReadProject.Response readProject(@PathVariable BigInteger projectId){
    return ReadProject.Response.fromEntity(
        projectService.readProject(projectId)
    );
  }

  // 프로젝트 구인 글 수정
  @PutMapping("/projects")
  public UpdateProject.Response updateProject(
      @RequestBody @Valid UpdateProject.Request request
//      ,Authentication authentication
  ){
    return UpdateProject.Response.fromEntity(
        projectService.updateProject(
            request.getUserId(), request.getProjectId(), request.getTitle(),
            request.getContent(), request.getProjectOnOffline(), request.getPlace(),
            request.getNumberOfRecruit(), request.getDueDate()
        )
    );
  }

  // 프로젝트 구인 글 삭제
  @DeleteMapping("/projects/{projectId}")
  public DeleteProject.Response deleteProject(@PathVariable BigInteger projectId,
    @RequestBody @Valid DeleteProject.Request request){
    return DeleteProject.Response.fromEntity(
        projectService.deleteProject(request.getUserId(), projectId)
    );
  }

  // 프로젝트 모든 구인 글 조회 ( 메인 화면, 구인 신청 가능한 글만, page 요소 추가 ) - 추후 구현 예정
//  @GetMapping
//  public ResponseEntity getArticles(@RequestParam(value = "page", required = false, defaultValue = "1") @Positive int page,
//      @RequestParam("size") @Positive int size) {
//
//    Page<Project> projectPage = projectService.findProjects(page, size);
//    List<Project> projects = projectPage.getContent();
//    List<ProjectResponseDto> projectResponseDtos = mapper.projectsToProjectResponseDtos(projects);
//    PageInfo pageInfo = new PageInfo(projectPage.getNumber() + 1, projectPage.getSize(),
//        projectPage.getTotalPages());
//
//    return new ResponseEntity(new ResponseDto<>(projectResponseDtos, pageInfo), HttpStatus.OK);
//  }


}

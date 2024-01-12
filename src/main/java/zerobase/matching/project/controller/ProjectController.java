package zerobase.matching.project.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
import zerobase.matching.project.dto.paging.ProjectPagingResponse;
import zerobase.matching.project.dto.ReadProject;
import zerobase.matching.project.dto.UpdateProject;
import zerobase.matching.project.service.ProjectService;

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
    CreateProject.Response response = CreateProject.Response.fromEntity(
        projectService.createProject(
            request.getUserId(), request.getTitle(), request.getContent(),
            request.getProjectOnOffline(), request.getPlace(),
            request.getNumberOfRecruit(), request.getDueDate()
        )
    );
    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 읽기
  @GetMapping("/projects")
  public ResponseEntity<ReadProject.Response> readProject(
      @RequestParam(value = "projectId") long projectId){
    ReadProject.Response response = ReadProject.Response.fromEntity(
        projectService.readProject(projectId)
    );
    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 수정
  @PutMapping("/projects")
  public ResponseEntity<UpdateProject.Response> updateProject(
      @RequestBody @Valid UpdateProject.Request request
  ){
    UpdateProject.Response response = UpdateProject.Response.fromEntity(
        projectService.updateProject(
            request.getUserId(), request.getProjectId(), request.getTitle(),
            request.getContent(), request.getProjectOnOffline(), request.getPlace(),
            request.getNumberOfRecruit(), request.getDueDate()
        )
    );
    return ResponseEntity.ok(response);
  }

  // 프로젝트 구인 글 삭제
  @DeleteMapping("/projects")
  public ResponseEntity<DeleteProject.Response> deleteProject(@RequestParam long projectId,
    @RequestBody @Valid DeleteProject.Request request){
    DeleteProject.Response response = DeleteProject.Response.fromEntity(
        projectService.deleteProject(request.getUserId(), projectId)
    );
    return ResponseEntity.ok(response);
  }

  // 프로젝트 모든 구인 글 조회 ( 메인 화면, page 요소 추가 )
  @GetMapping("/projects/list")
  public ProjectPagingResponse pagingProjects(
      @RequestParam(value = "page", required = false, defaultValue = "1") @Positive int page,
      @RequestParam("size") @Positive int size) {

    return projectService.pagingProjects(page, size);
  }


}

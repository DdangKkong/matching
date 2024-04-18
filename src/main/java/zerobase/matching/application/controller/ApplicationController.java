package zerobase.matching.application.controller;

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
import zerobase.matching.application.dto.CreateApplication;
import zerobase.matching.application.dto.ReadApplication;
import zerobase.matching.application.dto.RetrieveApplication;
import zerobase.matching.application.dto.SendApplication;
import zerobase.matching.application.dto.UpdateApplication;
import zerobase.matching.application.dto.paging.ApplicationPagingResponse;
import zerobase.matching.application.service.ApplicationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maching/projects/applications")
public class ApplicationController {

  private final ApplicationService applicationService;

  // 신청서 작성
  @PostMapping
  public ResponseEntity<CreateApplication.Response> createApplication(
      @RequestBody @Valid CreateApplication.Request request){
    CreateApplication.Response response = CreateApplication.Response.fromEntity(
        applicationService.createApplication(request));
    return ResponseEntity.ok(response);
  }

  // 신청서 제출
  @PutMapping("/submit")
  public ResponseEntity<SendApplication.Response> sendApplication(
      @RequestParam int projectId,
      @RequestBody @Valid SendApplication.Request request) {
    SendApplication.Response response = SendApplication.Response.fromEntity(
        applicationService.sendApplication(projectId ,request)
    );
    return ResponseEntity.ok(response);
  }

  // 신청서 회수
  @PutMapping("/retrieve")
  public ResponseEntity<RetrieveApplication.Response> retrieveApplication(
          @RequestBody @Valid RetrieveApplication.Request request) {
    RetrieveApplication.Response response = RetrieveApplication.Response.fromEntity(
            applicationService.retrieveApplication(request)
    );
    return ResponseEntity.ok(response);
  }

  // 신청서 읽기
  @GetMapping
  public ResponseEntity<ReadApplication.Response> readApplication(
      @RequestParam int applicationId,
      @RequestParam int userId
  ){
    ReadApplication.Response response = ReadApplication.Response.fromEntity(
        applicationService.readApplication(applicationId, userId)
    );
    return ResponseEntity.ok(response);
  }

  // 신청서 수정
  @PutMapping
  public ResponseEntity<UpdateApplication.Response> updateApplication(
      @RequestParam int applicationId,
      @RequestBody @Valid UpdateApplication.Request request){
    UpdateApplication.Response response = UpdateApplication.Response.fromEntity(
        applicationService.updateApplication(applicationId, request)
    );
    return ResponseEntity.ok(response);
  }

  // 신청서 삭제
  @DeleteMapping
  public ResponseEntity<String> deleteApplication(
      @RequestParam int applicationId,
      @RequestParam int userId
  ){
        applicationService.deleteApplication(applicationId, userId);
    return ResponseEntity.ok("Delete Complete");
  }

  // 자신의 신청서 리스트업 ( page 요소 추가 )
    @GetMapping("/list")
    public ApplicationPagingResponse pagingApplications(
        @RequestParam(value = "page", required = false, defaultValue = "1") @Positive int page,
        @RequestParam("size") @Positive int size) {

      return applicationService.pagingApplications(page-1, size); // 우리가 생각하는 1 페이지는 page 값이 0일때 이다
    }

}

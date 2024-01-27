package zerobase.matching.project.recruitment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.project.recruitment.dto.MinusMember;
import zerobase.matching.project.recruitment.dto.PlusMember;
import zerobase.matching.project.recruitment.service.RecruitmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/recruitment")
public class RecruitmentController {

  private final RecruitmentService recruitmentService;

  // 모집 인원에서 현재 인원 추가 ( 구인 게시글에서 팀원을 뽑았을 때 )
  @PutMapping("/plus")
  public ResponseEntity<PlusMember.Response> plusMember(
      @RequestBody @Valid PlusMember.Request request){
    PlusMember.Response response = PlusMember.Response.fromEntity(
        recruitmentService.plusMember(request)
    );
    return ResponseEntity.ok(response);
  }

  // 모집 인원에서 현재 인원 빼기 ( 구인 게시글에서 팀원 뽑은것을 취소할 때 )
  @PutMapping("/minus")
  public ResponseEntity<MinusMember.Response> minusMember(
          @RequestBody @Valid MinusMember.Request request){
    MinusMember.Response response = MinusMember.Response.fromEntity(
            recruitmentService.MinusMember(request)
    );
    return ResponseEntity.ok(response);
  }

}

package zerobase.matching.project.evaluation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
import zerobase.matching.project.evaluation.dto.CreateEvaluation;
import zerobase.matching.project.evaluation.dto.EvaluationDto;
import zerobase.matching.project.evaluation.dto.ReadEvaluation;
import zerobase.matching.project.evaluation.dto.ReadEvaluationAboutMe;
import zerobase.matching.project.evaluation.dto.UpdateEvaluation;
import zerobase.matching.project.evaluation.dto.paging.EvaluationPagingResponse;
import zerobase.matching.project.evaluation.dto.paging.EvaluationPagingResponseAboutMe;
import zerobase.matching.project.evaluation.service.EvaluationService;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class EvaluationController {

    private final EvaluationService evaluationService;

    // 팀원 평가하기
    @PostMapping("/maching/projects/evaluations")
    public ResponseEntity<CreateEvaluation.Response> createEvaluation (
            @RequestBody @Valid CreateEvaluation.Request request
    ){

        EvaluationDto evaluationDto = evaluationService.createEvaluation(request);

        return ResponseEntity.ok(CreateEvaluation.Response.fromDto(evaluationDto));

    }

    // 평가 읽기 ( 작성자 )
    @GetMapping("/maching/projects/evaluations")
    public ResponseEntity<ReadEvaluation.Response> readEvaluation (
            @RequestParam(value = "evaluationId") int evaluationId,
            @RequestParam(value = "userId") int userId
    ){
        EvaluationDto evaluationDto = evaluationService.readEvaluation(evaluationId, userId);

        return ResponseEntity.ok(ReadEvaluation.Response.fromDto(evaluationDto));
    }

    // 평가 수정하기
    @PutMapping("/maching/projects/evaluations")
    public ResponseEntity<UpdateEvaluation.Response> updateEvaluation (
            @RequestParam(value = "evaluationId") int evaluationId,
            @RequestBody @Valid UpdateEvaluation.Request request
    ){
        EvaluationDto evaluationDto = evaluationService.updateEvaluation(evaluationId, request);

        return ResponseEntity.ok(UpdateEvaluation.Response.fromDto(evaluationDto));
    }

    // 평가 삭제하기 ( 작성자만 삭제 가능 )
    @DeleteMapping("/maching/projects/evaluations")
    public ResponseEntity<String> deleteEvaluation (
            @RequestParam(value = "evaluationId") int evaluationId,
            @RequestParam(value = "userId") int userId
    ){
        evaluationService.deleteEvaluation(evaluationId, userId);

        return ResponseEntity.ok("Delete complete");
    }

    // 작성한 모든 평가 리스트 조회하기 ( paging ) / userId 를 @RequestBody 로 받으면 400 에러가 난다, 보통 Get 메서드에는 값을 파라미터로 받는게 일반적이라고 한다.
    @GetMapping("/maching/projects/evaluations/list")
    public EvaluationPagingResponse pagingEvaluations(
            @RequestParam(value = "page", required = false, defaultValue = "1") @Positive int page,
            @RequestParam(value = "size") @Positive int size,
            @RequestParam(value = "userId") int userId) {

        return evaluationService.pagingEvaluations(page-1, size, userId);
    }

    // 받은 평가 읽기 ( 1개씩, 평가자의 정보는 공개하지 않음 )
    @GetMapping("/maching/projects/evaluations/{evaluatedUserId}/{evaluationId}"
//            "/evaluations/evaluated_user" // 이거랑 "/evaluations/user" 이걸로 했을 때 왜인지 모르겠지만 계속 404 에러가 나서 그냥 파라미터만 넣음
    )
    public ResponseEntity<ReadEvaluationAboutMe.Response> readEvaluationAboutMe (
            @PathVariable("evaluatedUserId") int evaluatedUserId, // postman 에서 요청보낼때 url에 ":" 붙여서 값 넣기
            @PathVariable("evaluationId") int evaluationId
    ) {
        EvaluationDto evaluationDto = evaluationService.readEvaluationAboutMe(evaluatedUserId , evaluationId);

        // 평가 작성자 정보 초기화
        evaluationDto.setUserId(0);

        return ResponseEntity.ok(ReadEvaluationAboutMe.Response.fromDto(evaluationDto));
    }

    // 받은 모든 평가 리스트 조회하기 ( 평군 score 점수도 같이 조회하기 )
    @GetMapping("/maching/projects/evaluations/users/list")
    public EvaluationPagingResponseAboutMe pagingEvaluationAboutMe(
            @RequestParam(value = "page", required = false, defaultValue = "1") @Positive int page,
            @RequestParam(value = "size") @Positive int size,
            @RequestParam(value = "evaluatedUserId") @Positive int evaluatedUserId
    ) {

        return evaluationService.pagingEvaluationAboutMe(page-1, size, evaluatedUserId);
    }

}

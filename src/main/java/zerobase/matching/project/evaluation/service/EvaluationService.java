package zerobase.matching.project.evaluation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.evaluation.domain.Evaluation;
import zerobase.matching.project.evaluation.dto.*;
import zerobase.matching.project.evaluation.dto.paging.EvaluationPagingResponse;
import zerobase.matching.project.evaluation.dto.paging.EvaluationPagingResponseAboutMe;
import zerobase.matching.project.evaluation.dto.paging.EvaluationResponseAboutMeDto;
import zerobase.matching.project.evaluation.dto.paging.EvaluationResponseDto;
import zerobase.matching.project.evaluation.repository.EvaluationRepository;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final UserRepository userRepository;
    private final EvaluationRepository evaluationRepository;

    // 팀원 평가하기
    public EvaluationDto createEvaluation(CreateEvaluation.Request request) {

        UserEntity user = getUser(request.getUserId());

        Evaluation evaluation = evaluationRepository.save(
                Evaluation.builder()
                        .score(request.getScore())
                        .content(request.getContent())
                        .evaluatedUserId(request.getEvaluatedUserId())
                        .user(user)
                        .build()
        );

        return EvaluationDto.fromEntity(evaluation);
    }

    // 평가 읽기
    public EvaluationDto readEvaluation(int evaluationId, ReadEvaluation.Request request) {
        Evaluation evaluation = getEvaluation(evaluationId);

        // 작성자인지 확인
        if (request.getUserId() != evaluation.getUser().getUserId()) {
            throw new RuntimeException("조회 권한이 없는 회원입니다");
        }

        return EvaluationDto.fromEntity(evaluation);
    }

    // 평가 수정하기
    public EvaluationDto updateEvaluation(int evaluationId, UpdateEvaluation.Request request) {
        Evaluation evaluation = getEvaluation(evaluationId);

        // 작성자인지 확인
        if (request.getUserId() != evaluation.getUser().getUserId()) {
            throw new RuntimeException("수정 권한이 없는 회원입니다");
        }

        evaluation.setScore(request.getScore());
        evaluation.setContent(request.getContent());

        return EvaluationDto.fromEntity(evaluationRepository.save(evaluation));
    }

    // 평가 삭제하기
    public void deleteEvaluation(int evaluationId, DeleteEvaluation.Request request) {
        Evaluation evaluation = getEvaluation(evaluationId);

        // 작성자인지 확인
        if (request.getUserId() != evaluation.getUser().getUserId()) {
            throw new RuntimeException("삭제 권한이 없는 회원입니다");
        }

        evaluationRepository.delete(evaluation);
    }

    // 작성한 모든 평가 리스트 조회하기
    public EvaluationPagingResponse pagingEvaluations(int page, int size, int userId) {
        UserEntity user = getUser(userId);
        Pageable pageable = PageRequest.of(page, size);
        List<Evaluation> evaluationListBefore = evaluationRepository.findAllByUser(user);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), evaluationListBefore.size());
        Page<Evaluation> evaluationPage = new PageImpl<>(evaluationListBefore.subList(start, end), pageable, evaluationListBefore.size());

        List<Evaluation> evaluationList = evaluationPage.getContent();

        List<EvaluationResponseDto.Response> contents = evaluationList.stream().map(
                Evaluation -> EvaluationResponseDto.Response.fromEntity(Evaluation)).collect(Collectors.toList());


        return EvaluationPagingResponse.builder()
                .contents(contents)
                .pageNo(page)
                .pageSize(size)
                .totalElements(evaluationPage.getTotalElements())
                .totalPages(evaluationPage.getTotalPages())
                .build();
    }

    // 받은 평가 읽기
    public EvaluationDto readEvaluationAboutMe(int evaluatedUserId,int evaluationId) {
        UserEntity user = getUser(evaluatedUserId);
        Evaluation evaluation = getEvaluation(evaluationId);

        // 요청한 회원이 평가 받은 회원인지 확인
        if (evaluatedUserId != evaluation.getEvaluatedUserId()) {
            throw new RuntimeException("회원 정보가 일치하지 않아 조회가 불가능합니다");
        }

        return EvaluationDto.fromEntity(evaluation);
    }

    // 받은 모든 평가 리스트 조회하기, 평균 score 확인하기
    public EvaluationPagingResponseAboutMe pagingEvaluationAboutMe(int page, int size, int evaluatedUserId) {
        UserEntity user = getUser(evaluatedUserId);
        Pageable pageable = PageRequest.of(page, size);
        List<Evaluation> evaluationListBefore = evaluationRepository.findAllByEvaluatedUserId(evaluatedUserId);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), evaluationListBefore.size());
        Page<Evaluation> evaluationPage = new PageImpl<>(evaluationListBefore.subList(start, end), pageable, evaluationListBefore.size());

        List<Evaluation> evaluationList = evaluationPage.getContent();

        List<EvaluationResponseAboutMeDto.Response> contents = evaluationList.stream().map(
                Evaluation -> EvaluationResponseAboutMeDto.Response.fromEntity(Evaluation)).collect(Collectors.toList());

        // 받은 점수 평균내기
        List<Double> scoreList = evaluationListBefore.stream()
                .map(Evaluation::getScore)
                .collect(Collectors.toList());
        DoubleSummaryStatistics statistics = scoreList.stream().mapToDouble(num -> num).summaryStatistics();
        double avScore = statistics.getAverage();

        return EvaluationPagingResponseAboutMe.builder()
                .contents(contents)
                .pageNo(page)
                .pageSize(size)
                .totalElements(evaluationPage.getTotalElements())
                .totalPages(evaluationPage.getTotalPages())
                .avScore(avScore)
                .build();
    }

    private Evaluation getEvaluation(int evaluationId) {
        return evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("회원정보를 칮지 못했습니다"));
    }

    private UserEntity getUser(int userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("회원정보를 칮지 못했습니다"));
    }

}

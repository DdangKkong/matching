package zerobase.matching.project.evaluation.dto.paging;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.evaluation.domain.Evaluation;

public class EvaluationResponseDto {

    // id
    private int evaluationId;
    // 평가 점수
    private double score;
    // 상세 평가 내용
    private String content;
    // 평가 받을 회원
    private int evaluatedUserId;
    // 작성자
    private int userId;


    @Builder
    @Getter
    public static class Response{

        private int evaluationId;
        private double score;
        private String content;
        private int evaluatedUserId;
        private int userId;

        public static EvaluationResponseDto.Response fromEntity (Evaluation evaluation){
        return EvaluationResponseDto.Response.builder()
                .evaluationId(evaluation.getEvaluationId())
                .score(evaluation.getScore())
                .content(evaluation.getContent())
                .evaluatedUserId(evaluation.getEvaluatedUserId())
                .userId(evaluation.getUser().getUserId())
                .build();
    }
    }

}

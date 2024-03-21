package zerobase.matching.project.evaluation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.matching.project.evaluation.domain.Evaluation;

@Builder
@Getter
@Setter
public class EvaluationDto {

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


    public static EvaluationDto fromEntity (Evaluation evaluation) {
        return EvaluationDto.builder()
                .evaluationId(evaluation.getEvaluationId())
                .score(evaluation.getScore())
                .content(evaluation.getContent())
                .evaluatedUserId(evaluation.getEvaluatedUserId())
                .userId(evaluation.getUser().getUserId())
                .build();
    }

}

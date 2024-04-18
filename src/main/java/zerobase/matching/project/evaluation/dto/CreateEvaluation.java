package zerobase.matching.project.evaluation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CreateEvaluation {

    @Getter
    @Setter
    @Builder
    public static class Request {

        @NotNull
        private int userId;
        @NotNull
        private double score;
        @NotBlank
        private String content;
        @NotNull
        private int evaluatedUserId;

    }

    @Builder
    @Getter // 없으면 응답값을 json 으로 가져오지 못해서 406 에러 발생
    public static class Response {

        private int evaluationId;

        private double score;

        private String content;

        private int evaluatedUserId;

        private int userId;


        public static Response fromDto(EvaluationDto evaluationDto) {
            return Response.builder()
                    .evaluationId(evaluationDto.getEvaluationId())
                    .score(evaluationDto.getScore())
                    .content(evaluationDto.getContent())
                    .evaluatedUserId(evaluationDto.getEvaluatedUserId())
                    .userId(evaluationDto.getUserId())
                    .build();
        }

    }


}

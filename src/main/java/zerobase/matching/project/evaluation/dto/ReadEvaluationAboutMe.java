package zerobase.matching.project.evaluation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class ReadEvaluationAboutMe {

    @Getter
    public static class Request {

        @NotNull
        private int evaluationId;
        @NotNull
        private int evaluatedUserId;

    }

    @Builder
    @Getter
    public static class Response {

        private int evaluationId;

        private double score;

        private String content;

        private int evaluatedUserId;

        public static Response fromDto(EvaluationDto evaluationDto) {
            return Response.builder()
                    .evaluationId(evaluationDto.getEvaluationId())
                    .score(evaluationDto.getScore())
                    .content(evaluationDto.getContent())
                    .evaluatedUserId(evaluationDto.getEvaluatedUserId())
                    .build();
        }

    }


}

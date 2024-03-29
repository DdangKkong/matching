package zerobase.matching.project.evaluation.dto;

import lombok.Builder;
import lombok.Getter;

public class ReadEvaluationAboutMe {

    @Getter
//    @Builder
    public static class Request {

        private int evaluationId;
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
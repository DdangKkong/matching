package zerobase.matching.project.evaluation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ReadEvaluation {

    @Getter
    @Setter
//    @Builder 빌더가 있으면 400 (Bad request) 뜬다, 아마 userId 라는 객체를 생성 못하는듯 하다
    public static class Request {

        private int userId;

    }

    @Builder
    @Getter
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

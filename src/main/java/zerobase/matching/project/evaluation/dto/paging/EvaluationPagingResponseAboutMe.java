package zerobase.matching.project.evaluation.dto.paging;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationPagingResponseAboutMe {

  private List<EvaluationResponseAboutMeDto.Response> contents;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private double avScore;

}

package zerobase.matching.project.evaluation.dto.paging;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationPagingResponse {

  private List<EvaluationResponseDto.Response> contents;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;

}

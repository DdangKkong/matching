package zerobase.matching.project.dto.paging;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectPagingResponse {

  private List<ProjectResponseDto.Response> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;

}

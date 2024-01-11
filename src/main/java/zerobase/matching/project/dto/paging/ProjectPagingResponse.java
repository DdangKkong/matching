package zerobase.matching.project.dto.paging;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

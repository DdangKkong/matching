package zerobase.matching.application.dto.paging;

import lombok.*;
import zerobase.matching.application.dto.ApplicationDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationPagingResponse {

  private List<ApplicationDto> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;

}

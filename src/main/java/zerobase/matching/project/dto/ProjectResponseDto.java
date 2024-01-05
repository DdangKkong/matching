package zerobase.matching.project.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.project.domain.ProjectOnOffline;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponseDto {
  // 구인 글 id
  private BigInteger projectId;

  // 회원 id
  private BigInteger userId;

  // 제목
  private String title;

  // 내용
  private String content;

  // 모임 형태
  private ProjectOnOffline projectOnOffline;

  // 모임 장소
  private String place;

  // 모집 인원
  private int numberOfRecruit;

  // 현재 인원
  private int currentRecruit;

  // 작성 일시
  private LocalDateTime createTime;

  // 수정 일시
  private LocalDateTime updateTime;

  // 구인 마감 날짜
  private LocalDate dueDate;


}

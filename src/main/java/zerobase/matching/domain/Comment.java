package zerobase.matching.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger commentId;

  // 내용
  @Column(name = "content")
  private String content;

  // 댓글 작성 일시
  @Column(name = "create_time")
  private LocalDateTime createTime;

  // 댓글 수정 일시
  @Column(name = "update_time")
  private LocalDateTime updateTime;

  // 댓글 삭제 일시
  @Column(name = "delete_time")
  private LocalDateTime deleteTime;

  // 부모 댓글 고유번호
  @Column(name = "parent_id")
  private BigInteger parentId;

  // 계층
  @Column(name = "level")
  private int level;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project ;

}

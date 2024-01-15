package zerobase.matching.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.project.domain.Project;
import zerobase.matching.user.persist.entity.UserEntity;

@Getter // Dto 에서 builder.get~~ 하기 위해
@Setter // 구인 글 수정할때 project.set~~ 하기 위해
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "COMMENT_ID")
  private long commentId;

  // 내용
  @Column(name = "CONTENT")
  private String content;

  // 댓글 작성 일시
  @Column(name = "CREATED_AT")
  private LocalDateTime createTime;

  // 댓글 수정 일시
  @Column(name = "UPDATED_AT")
  private LocalDateTime updateTime;

  // 댓글 삭제 일시
  @Column(name = "DELETED_AT")
  private LocalDateTime deleteTime;

  // 부모 댓글 고유번호
  @Column(name = "PARENT_ID")
  private long parentId;

  // 계층
  @Column(name = "LEVEL")
  private int level;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private Project project ;

}

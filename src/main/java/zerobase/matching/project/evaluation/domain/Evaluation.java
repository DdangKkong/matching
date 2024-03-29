package zerobase.matching.project.evaluation.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.user.persist.entity.UserEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVALUATION")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVALUATION_ID")
    private int evaluationId;

    // 평가 점수
    @Column(name = "SCORE")
    private double score;

    // 상세 평가 내용
    @Column(name = "CONTENT")
    private String content;

    // 평가 받을 회원
    @Column(name = "EVALUATED_USER_ID")
    private int evaluatedUserId;

    // 작성자
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

}

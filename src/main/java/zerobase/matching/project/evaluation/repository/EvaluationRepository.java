package zerobase.matching.project.evaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.project.evaluation.domain.Evaluation;
import zerobase.matching.user.persist.entity.UserEntity;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findAllByUser(UserEntity user);
    List<Evaluation> findAllByEvaluatedUserId(int evaluatedUserId);
}

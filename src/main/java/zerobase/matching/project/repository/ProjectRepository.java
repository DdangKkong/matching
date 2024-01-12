package zerobase.matching.project.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.project.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findAllByDueDateBefore(LocalDate date);
}

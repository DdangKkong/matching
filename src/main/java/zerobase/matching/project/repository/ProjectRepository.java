package zerobase.matching.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.project.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}

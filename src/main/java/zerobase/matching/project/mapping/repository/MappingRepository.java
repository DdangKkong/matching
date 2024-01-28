package zerobase.matching.project.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.project.mapping.domain.MappingProjectRecruit;

import java.util.List;

@Repository
public interface MappingRepository extends JpaRepository<MappingProjectRecruit, Integer> {
  List<MappingProjectRecruit> findAllByProjectProjectId(int projectId);

}

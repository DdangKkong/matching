package zerobase.matching.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.application.domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}

package zerobase.matching.user.persist;

import java.math.BigInteger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.user.persist.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUserLoginId(String userLoginId);
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByNickname(String nickname);
}

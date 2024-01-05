package zerobase.matching.repository;

import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.matching.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {

}

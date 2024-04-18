package zerobase.matching.user.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import zerobase.matching.user.exception.CustomException;
import zerobase.matching.user.exception.ErrorCode;
import zerobase.matching.user.persist.UserRepository;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect implements UserDetailsService {

  private final UserRepository userRepository;

  /** 회원 상세 정보 불러오기 :
   아이디, 비밀번호 외의 정보를 불러오기 위해서는 UserEntity 타입으로 형변환 해야한다. */
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return this.userRepository.findByUserLoginId(userId)
        .orElseThrow(()-> new CustomException(ErrorCode.USERLOGINID_NOTFOUND));
  }
}
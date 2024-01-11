package zerobase.matching.user.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.matching.security.JwtUtil;
import zerobase.matching.user.aop.AuthAspect;
import zerobase.matching.user.dto.SignIn.Request;
import zerobase.matching.user.dto.SignUp;
import zerobase.matching.user.exception.AppException;
import zerobase.matching.user.exception.ErrorCode;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthAspect authAspect;

  /**시크릿 키*/
  @Value("${jwt.token.secretKey}")
  private String secretKey;
  /**토큰 만료 시각*/
  private Long expiredAtMs = 1000 * 60 * 60L;

  /** 회원 가입 */
  public SignUp.Response register(SignUp.Request request) {

    // # case 1. 사용 불가능한 아이디인지 확인
    userRepository.findByUserLoginId(request.getUserLoginId())
        .ifPresent(e -> {
          throw new AppException(ErrorCode.USERLOGINID_DUPLICATED);
        });

    // # case 2. 사용 불가능한 이메일인지 확인
    userRepository.findByEmail(request.getEmail())
        .ifPresent(e -> {
          throw new AppException(ErrorCode.EMAIL_DUPLICATED);
        });

    // # case 3. 사용 불가능한 닉네임인지 확인
    userRepository.findByNickname(request.getNickname())
        .ifPresent(e -> {
          throw new AppException(ErrorCode.NICKNAME_DUPLICATED);
        });

    // # case 4. 비밀번호 검증1
    // 패스워드 정규표현식
    // 사용 가능한 패스워드인지 검증하는 용도 : 알파벳, 숫자, 특수문자가 포함된 8~20자 길이의 문자열
    Pattern pwPat = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
    Matcher pwPatMatcher = pwPat.matcher(request.getPassword());
    // 사용 불가능한 이유를 가려내기 위한 용도1 : 길이 미달
    Pattern pwPatLen = Pattern.compile("^(.{8,20})$");
    Matcher pwPatLenMatcher = pwPatLen.matcher(request.getPassword());
    // 검증
    if(!pwPatMatcher.matches()){
      if(!pwPatLenMatcher.matches()){
        throw new AppException(ErrorCode.PASSWORD_NOT_ALLOW_LENGTH);
      } else {
        throw new AppException(ErrorCode.PASSWORD_NOT_ALLOW_STRING);
      }
    }

    // # case 5. 비밀번호 검증2 (최초 입력 비밀번호와 재입력한 비밀번호가 동일한지 검증)
    if(!request.getPassword().equals(request.getPasswordCheck())){
      throw new AppException(ErrorCode.PASSWORD_NOT_CHECK);
    }

    // # 패스워드 암호화
    request.setPassword(this.passwordEncoder.encode(request.getPassword()));

    // # DB에 저장
    UserEntity result = this.userRepository.save(request.toEntity());

    // # 결과 반환
    return SignUp.Response.builder()
        .userId(result.getUserId())
        .registerTime(result.getRegisterTime())
        .userLoginId(result.getUserLoginId())
        .nickname(result.getNickname())
        .email(result.getEmail())
        .git(result.getGit())
        .phoneNumber(result.getPhoneNumber())
        .birthDate(result.getBirthDate())
        .gender(result.getGender().toString()) // enum -> String 변환
        .residence(result.getResidence())
        .onOffline(result.getOnOffline().toString())
        .portfolio(result.getPortfolio())
        .role(result.getRole().toString())
        .membershipLevel(result.getMembershipLevel().toString())
        .bank(result.getBank())
        .accountNumber(result.getAccountNumber())
        .build();
  }

  public String login(Request request) {

    String inputUserId = request.getUserLoginId();
    String inputPassword = request.getPassword();

    // # case 1. userId 존재하지 않음
    // 사용자가 입력한 아이디 값(inputUserId)이 DB에 존재하는지 여부를 확인한다.
    // 존재하지 않으면 예외처리를 한다.
    UserEntity selectedUser = (UserEntity) authAspect.loadUserByUsername(inputUserId);

    // # case 2. 이미 탈퇴된 회원임
    // 탈퇴를 하여 더이상 사용할 수 없는 아이디로 로그인할 경우 예외 처리를 한다.
    if(!selectedUser.isEnabled()){
      throw new AppException(ErrorCode.USERLOGINID_INVALID);
    }

    // # case 3. password 틀림
    // userId가 DB에 존재해서 selectedMember가 정상적으로 리턴되었을 경우,
    // 사용자가 입력한 패스워드(inputPassword)가 DB에 저장된 패스워드와 일치하는지 비교한다.
    // 일치하지 않으면 예외처리를 한다.
    // (DB의 패스워드는 암호화되어 저장되어있기 때문에 passwordEncoder 객체의 매서드 matches를 이용해야한다.)
    if(!passwordEncoder.matches(inputPassword, selectedUser.getPassword())) {
      throw new AppException(ErrorCode.PASSWORD_INVALID);
    }

    // # accessToken 생성
    String accessToken = JwtUtil.createJwtToken(selectedUser.getUserLoginId(), secretKey, expiredAtMs);

    // # accessToken 발행
    return accessToken;
  }

  public UserEntity findUser(Long userId){
    return userRepository.findByUserId(userId).orElseThrow(
            ()-> new RuntimeException("NoUser")
    );
  }
}

package zerobase.matching.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 참고한 코드 : https://velog.io/@rungoat/SpringBoot-Custom-Exception-처리

/** 에러 코드를 모아놓은 enum 객체 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
  USERLOGINID_DUPLICATED(HttpStatus.CONFLICT, "USER-USERLOGINID-001", "이미 존재하는 아이디입니다."),
  USERLOGINID_NOTFOUND(HttpStatus.NOT_FOUND, "USER-USERLOGINID-002", "해당 아이디를 가진 유저를 찾을 수 없습니다. "),
  USERLOGINID_INVALID(HttpStatus.CONFLICT, "USER-USERLOGINID-003", ""),
  PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "USER-PASSWORD-001", "패스워드를 잘못 입력하였습니다."),
  PASSWORD_NOT_CHANGED(HttpStatus.CONFLICT, "USER-PASSWORD-002", ""),
  PASSWORD_NOT_CONFIRM(HttpStatus.CONFLICT, "USER-PASSWORD-003", ""),
  PASSWORD_NOT_ALLOW_LENGTH(HttpStatus.CONFLICT, "USER-PASSWORD-004", "최소 8자 이상 최대 20자 이하 길이의 패스워드만 가능합니다."),
  PASSWORD_NOT_ALLOW_STRING(HttpStatus.CONFLICT, "USER-PASSWORD-005", "알파벳, 숫자, 특수문자를 각각 최소 1개 이상 포함시켜야 합니다."),
  PASSWORD_NOT_CHECK(HttpStatus.CONFLICT, "USER-PASSWORD-006", "입력한 패스워드와 확인용 패스워드가 일치하지 않습니다."),
  EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER-EMAIL-001", "이미 존재하는 이메일입니다."),
  NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "USER-NICKNAME-001", "이미 존재하는 닉네임입니다."),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
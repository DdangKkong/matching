package zerobase.matching.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 참고한 코드 : https://velog.io/@rungoat/SpringBoot-Custom-Exception-처리

/** 에러 코드를 모아놓은 enum 객체 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
  USERLOGINID_DUPLICATED(HttpStatus.CONFLICT, "USER-001", "이미 존재하는 아이디입니다."),
  USERLOGINID_NOTFOUND(HttpStatus.NOT_FOUND, "USER-002", "해당 아이디를 가진 유저를 찾을 수 없습니다."),
  PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "USER-003", "패스워드를 잘못 입력하였습니다."),
  PASSWORD_NOT_ALLOW_LENGTH(HttpStatus.BAD_REQUEST, "USER-004", "최소 8자 이상 최대 20자 이하 길이의 패스워드만 가능합니다."),
  PASSWORD_NOT_ALLOW_STRING(HttpStatus.BAD_REQUEST, "USER-005", "알파벳, 숫자, 특수문자를 각각 최소 1개 이상 포함시켜야 합니다."),
  PASSWORD_NOT_CHECK(HttpStatus.BAD_REQUEST, "USER-006", "입력한 패스워드와 확인용 패스워드가 일치하지 않습니다."),
  EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER-007", "이미 존재하는 이메일입니다."),
  NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "USER-008", "이미 존재하는 닉네임입니다."),
  USERID_INVALID(HttpStatus.FORBIDDEN, "USER-009", "알맞지 않은 유저 정보입니다."),
  USER_IN_CHAT_ROOM_NOTFOUND(HttpStatus.NOT_FOUND, "USER-010", "채팅방에 해당 유저를 찾을 수 없습니다."),


  CHAT_ROOM_INVALID(HttpStatus.BAD_REQUEST, "CHAT-ROOM-001", "알맞지 않은 채팅방 정보입니다."),
  USER_CHAT_ROOM_INVALID(HttpStatus.BAD_REQUEST, "USER-CHAT-ROOM-001", "알맞지 않은 유저채팅방 정보입니다."),
  PROJECT_DUE_DATE_INVALID(HttpStatus.BAD_REQUEST, "PROJECT-001", "DueDate 를 잘못 입력하셨습니다."),
  PROJECTID_INVALID(HttpStatus.FORBIDDEN, "PROJECT-002", "알맞지 않은 게시글 정보입니다."),
  RECRUITMENTID_INVALID(HttpStatus.FORBIDDEN, "RECRUITMENT-001", "알맞지 않은 모집 현황 정보입니다."),
  RECRUITMENT_NUMBER_INVALID(HttpStatus.BAD_REQUEST, "RECRUITMENT-002", "모집 인원이 모집된 인원보다 적습니다."),
  RECRUITMENT_NUMBER_EXCEED(HttpStatus.BAD_REQUEST, "RECRUITMENT-003", "모집 인원이 이미 다 찼습니다."),
  RECRUITMENT_NUMBER_NOT_MINUS(HttpStatus.BAD_REQUEST, "RECRUITMENT-004", "모집 인원은 음수가 될 수 없습니다."),
  APPLICATIONID_INVALID(HttpStatus.FORBIDDEN, "APPLICATION-001", "알맞지 않은 신청서 정보입니다."),
  COMMENTID_INVALID(HttpStatus.FORBIDDEN, "COMMENT-001", "알맞지 않은 댓글 정보입니다."),
  DEPARTMENT_NOT_MATCH(HttpStatus.BAD_REQUEST, "DEPARTMENT-001", "일치하는 모집군이 없습니다."),
  EVALUATEDUSER_INVALID(HttpStatus.FORBIDDEN, "EVALUATION-001", "평가 받은 유저 정보가 알맞지 않습니다."),
  EVALUATION_INVALID(HttpStatus.FORBIDDEN, "EVALUATION-002", "팀원 평가 정보가 알맞지 않습니다."),




  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
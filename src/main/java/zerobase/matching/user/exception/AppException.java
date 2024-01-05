package zerobase.matching.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 사용자 지정 예외처리 */
@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{

  private ErrorCode errorCode;
}
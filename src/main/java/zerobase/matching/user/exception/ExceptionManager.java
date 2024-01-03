package zerobase.matching.user.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Controller 전역에서 발생하는 Custom Error를 잡아주는 기능을 한다. */
@RestControllerAdvice
public class ExceptionManager {

  @ExceptionHandler(AppException.class)
  /** 예외 발생 시, 상태코드(status), 예외이름(name), 에러코드(errorCode), 에러메시지(errorMessage)를 출력한다. */
  public ResponseEntity<ErrorResponseEntity> appExceptionHandler(AppException e){
    return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
  }
}
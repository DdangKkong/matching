package zerobase.matching.user.persist.annotation;

// 참고한 코드 :
// https://velog.io/@devmizz/Spring-Request로-Enum이-들어올-때
// https://velog.io/@hellozin/Annotation으로-Enum-검증하기

import jakarta.validation.Constraint; // javax -> jakarta로 패키지명 변경
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Enum 타입으로 입력된 request 데이터를 검증하기 위한 어노테이션 (직접 생성)*/
// 해당 annotation이 실행 할 ConstraintValidator 구현체를 `EnumValidator`로 지정
@Constraint(validatedBy = {EnumValidator.class})
// 해당 annotation은 메소드, 필드, 파라미터에 적용
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
// annotation을 Runtime까지 유지
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum {
  String message() default "Invalid value. This is not permitted.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  /** 어떤 enum 타입 데이터를 검증할지 설정 */
  Class<? extends java.lang.Enum<?>> enumClass();
  /** 대소문자 구분할지 설정 */
  boolean ignoreCase() default false;
}

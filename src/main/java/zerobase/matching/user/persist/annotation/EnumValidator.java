package zerobase.matching.user.persist.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/** 어노테이션 Enum의 구현체 클래스. */
public class EnumValidator implements ConstraintValidator<Enum, String> {

  private Enum annotation;

  @Override
  public void initialize(Enum constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean result = false;
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();
    if (enumValues != null) {
      for (Object enumValue : enumValues) {
        if (value.equals(enumValue.toString())
            || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
          result = true;
          break;
        }
      }
    }
    return result;
  }

}
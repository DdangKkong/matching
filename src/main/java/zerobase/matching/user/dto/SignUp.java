package zerobase.matching.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import zerobase.matching.user.persist.annotation.Enum;
import zerobase.matching.user.persist.consist.Gender;
import zerobase.matching.user.persist.consist.MembershipLevel;
import zerobase.matching.user.persist.consist.OnOffline;
import zerobase.matching.user.persist.consist.Role;
import zerobase.matching.user.persist.entity.UserEntity;

/** 회원 가입 시에 사용될 dto 객체*/
public class SignUp {

  /** 회원 가입 시 데이터를 입력받을 dto 객체*/
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request{

    /** 로그인 ID */
    @NotBlank
    private String userLoginId;

    /** 비밀번호 */
    @NotBlank
    private String password;

    /** 비밀번호 재입력 */
    @NotBlank
    private String passwordCheck;

    /**  */
    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    private String git;

    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Enum(enumClass = Gender.class, ignoreCase = true)
    private String gender;

    private String residence;

    @Enum(enumClass = OnOffline.class, ignoreCase = true)
    private String onOffline;

    private String portfolio;

    @Enum(enumClass = Role.class, ignoreCase = true)
    private String role;

    @Enum(enumClass = MembershipLevel.class, ignoreCase = true)
    private String membershipLevel;

    private String bank;

    private String accountNumber;

    /** SignUp DTO 객체를 엔티티로 변환 */
    public UserEntity toEntity(){
      return UserEntity.builder()
          .userLoginId(this.userLoginId)
          .password(this.password)
          .nickname(this.nickname)
          .email(this.email)
          .git(this.git)
          .phoneNumber(this.phoneNumber)
          .birthDate(this.birthDate)
          .gender(Gender.valueOf(this.gender.toLowerCase())) // string -> enum 변환을 위해 내장 메서드 valueOf()와 toLowerCase()사용
          .residence(this.residence)
          .onOffline(OnOffline.valueOf(this.onOffline.toLowerCase()))
          .portfolio(this.portfolio)
          .registerTime(Timestamp.valueOf(LocalDateTime.now()))
          .updateTime(Timestamp.valueOf(LocalDateTime.now()))
          .role(Role.valueOf(this.role.toLowerCase()))
          .membershipLevel(MembershipLevel.valueOf(this.membershipLevel.toLowerCase()))
          .teamLevel((double) 0)
          .bank(this.bank)
          .accountNumber(this.accountNumber)
          .build();
    }
  }
  // [질문] string 입력값을 enum으로 변환할 때, valueOf()와 toLowerCase()을 매번 사용했다.
  // 좀더 간결하게 코드를 짤 방법은 없을까?


  /** 회원 가입 완료 시 결과 값을 출력하기 위한 DTO 객체*/
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response{

    private Long userId;

    private Timestamp registerTime;
    private String userLoginId;
    private String nickname;
    private String email;
    private String git;
    private String phoneNumber;
    private LocalDate birthDate;
    private String gender;
    private String residence;
    private String onOffline;
    private String portfolio;
    private String role;
    private String membershipLevel;
    private String bank;
    private String accountNumber;
  }

}

package zerobase.matching.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 로그인 시에 사용될 dto 객체*/
public class SignIn {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request{

    private String userLoginId;
    private String password;
  }

}

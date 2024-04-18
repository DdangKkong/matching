package zerobase.matching.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.user.dto.SignIn;
import zerobase.matching.user.dto.SignUp;
import zerobase.matching.user.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/maching/users")
public class UserController {

  private final UserService userService;

  /**회원 가입 요청*/
  @PostMapping("/signup")
  public ResponseEntity<SignUp.Response> signup(@RequestBody @Valid SignUp.Request request){
    SignUp.Response result = this.userService.register(request);
    return ResponseEntity.ok().body(result);
  }

  /**로그인 요청 - access token을 리턴한다.*/
  @PostMapping("/signin")
  public ResponseEntity<String> singin(@RequestBody @Valid SignIn.Request request) {
    String token = this.userService.login(request);
    return ResponseEntity.ok().body(token);
  }

}

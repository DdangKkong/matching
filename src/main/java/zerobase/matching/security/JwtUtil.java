package zerobase.matching.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import zerobase.matching.user.aop.AuthAspect;
import zerobase.matching.user.service.UserService;

/** jwt 토큰과 관련된 기능이 들어있는 클래스 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final UserService userService;
  private final AuthAspect authAspect;

  /**시크릿 키*/
  @Value("${jwt.token.secretKey}")
  private String secretKey;


  /** jwt 토큰 발급 */
  public static String createJwtToken(String userLoginId, String secretKey, Long expiredMs){
    // # 클레임 생성 및 매핑
    Claims claims = Jwts.claims();
    claims.put("userName", userLoginId);

    // # jwt 토큰 생성 및 리턴
    return Jwts.builder()
        .setClaims(claims) // claim의 정보를 jwt토큰에 적용
        .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 생성 시각 설정
        .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시각 설정
        .signWith(SignatureAlgorithm.HS256, secretKey) // secreatKey 적용
        .compact(); // 토큰 생성
  }

  /** 토큰이 유효한지 검증 */
  public boolean isValidate(String token) {
    // # 토큰 값이 존재하는지 여부 확인
    if(!StringUtils.hasText(token)) return false;

    // # 토큰 만료 시각이 지났는지 확인
    Claims claims = this.parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  /** (private) 토큰으로부터 claim을 추출(파싱) */
  private Claims parseClaims(String token) {
    try{
      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e){
      return e.getClaims();
    }
  }

  /** 토큰으로부터 인증 정보를 가져오기 */
  public Authentication getAutentication(String token) {
    // # 유저 아이디를 토큰으로부터 가져온 뒤,
    // 유저 정보(UserDetails 타입)를 가져온다.
    UserDetails userDetails = authAspect.loadUserByUsername(this.getUserLoginId(token));

    // # 받아온 유저 정보(userDetails)를 스프링에서 지원해주는 형태의 토큰으로 변환, 리턴
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /** 유저 아이디(username)을 토큰으로부터 가져옴*/
  // 토큰의 Payload에 userName 키의 값이 바로 유저의 아이디(userLoginId)이다.
  public String getUserLoginId(String token){
    return this.parseClaims(token).get("userName").toString();
  }
}
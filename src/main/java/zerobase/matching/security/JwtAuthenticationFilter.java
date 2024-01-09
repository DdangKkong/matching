package zerobase.matching.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/** 리퀘스트를 맨 처음 받을 때, 인증을 수행하는 (필터링하는) 기능이 담긴 클래스*/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  // 어떤 key를 기준으로 토큰을 주고받을지 설정 (Header값)
  public static final String TOKEN_HEADER = "Authorization";
  // 인증 타입 (JWT는 Bearer)
  public static final String TOKEN_PREFIX = "Bearer "; // 한칸 띄어줘야함!

  /** 페이지에 접근하기 위한 권한을 부여하는 메서드(오버라이딩). */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // # 리퀘스트의 헤더로부터 토큰만 추출
    String token = this.resultTokenFromRequest(request);
    // # 유효한 토큰인지 검증
    if(this.jwtUtil.isValidate(token)){
      // # 인증 정보를 저장
      Authentication auth = this.jwtUtil.getAutentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

  /** 리퀘스트의 헤더로부터 토큰만 추출하는 메서드 */
  private String resultTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader(TOKEN_HEADER);
    // 토큰이 존재하고, 토큰이 JWT 종류일때
    // (즉, JWT 토큰의 형태를 띄고 있을때)
    if(!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)){
      // 토큰의 실질적인 내용만 추출해서 리턴
      return token.substring(TOKEN_PREFIX.length());
    }
    return null;
  }
}
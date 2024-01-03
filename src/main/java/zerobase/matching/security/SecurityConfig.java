package zerobase.matching.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{

  private final JwtAuthenticationFilter authenticationFilter;

  /** 모든 http 리퀘스트 정보를 일단 받아들인다.*/
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
        // # 토큰 방식을 사용할 것이기 때문에
        // httpBasic과 csrf 공격 방지 기능을 사용하지 않는다.
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        // # 페이지 인증 설정
        .authorizeHttpRequests(requests -> requests
            // * 다음 페이지는 인증 필요 없음(누구나 인증 없이 접근 가능)
            .requestMatchers("/users/signup", "/users/signin").permitAll()
            // * 나머지 페이지는 인증 필요
            .anyRequest().authenticated())
        // # 토큰 필터링 적용
        // (두번째 전달인자는 스프링 내장 필터)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
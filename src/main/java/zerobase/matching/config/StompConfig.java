package zerobase.matching.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // addEndpoint : 소켓 연결 URI -> Socket연결을 위한 것
        // setAllowedOriginPatterns : CORS 설정
        // withSockJS : 소켓을 지원하지 않는 브라우저이면 SockJS 사용하도록 설정
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 내장 브로커 사용
        // 해당 prefix가 붙은 경로일 때, 메세지를 브로커에서 바로 처리
        // queue는 1:1 , topic은 1:N
        registry.enableSimpleBroker("/queue", "topic");

        // 해당 prefix가 붙은 경로일 때, 메세지 핸들러로 전달
        registry.setApplicationDestinationPrefixes("/app");
    }
}

package kr.kro.namohagae.global.config;

import kr.kro.namohagae.global.hander.NotificationWebSocketHandler;
import kr.kro.namohagae.puching.websocket.ChatPuchingWebsocketHandler;
import kr.kro.namohagae.puching.websocket.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ChatPuchingWebsocketHandler chatImageWebsocketHandler;
    private final NotificationWebSocketHandler notificationWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 소켓연결 주소와 소켓접근가능 도메인 설정
        String os =  System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            registry.addHandler(chatWebSocketHandler,"/chatroom").setAllowedOrigins("https://namohagae.kro.kr","https://www.namohagae.kro.kr");
            registry.addHandler(chatImageWebsocketHandler,"/puching").setAllowedOrigins("https://namohagae.kro.kr","https://www.namohagae.kro.kr");
            registry.addHandler(notificationWebSocketHandler, "/notification").setAllowedOrigins("https://namohagae.kro.kr","https://namohagae.kro.kr");
        } else{
            registry.addHandler(chatWebSocketHandler,"/chatroom").setAllowedOrigins("http://localhost:8081");
            registry.addHandler(chatImageWebsocketHandler,"/puching").setAllowedOrigins(("http://localhost:8081"));
            registry.addHandler(notificationWebSocketHandler, "/notification").setAllowedOrigins("http://localhost:8081");
        }
    }
}

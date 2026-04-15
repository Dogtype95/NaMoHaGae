package kr.kro.namohagae.global.hander;

import kr.kro.namohagae.global.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// 알람 웹소켓 핸들러
@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    // 웹소켓 빈 주입
    @Autowired
    private WebSocketService webSocketService;

    // 웹소켓 연결 시작
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
       if(session.getPrincipal()!=null)
           webSocketService.connect(session);
    }

    // 웹소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(session.getPrincipal()!=null)
            webSocketService.disconnect(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}

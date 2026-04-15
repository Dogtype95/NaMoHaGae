package kr.kro.namohagae.global.websocket;

import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

// 유저 웹소켓 세션 정보
@Getter
public class WebSocketUser {
    private final String username;
    private final List<WebSocketSession> list; // 서버에 실시간으로 접속중인 유저들 목록 서버 다중화 할 시 확인 필요

    // 생성(생성자)
    public WebSocketUser(String username, WebSocketSession session){
        this.username = username;
        list = new Vector<>();
        this.list.add(session);
    }

    // 웹소켓 세션 유저추가
    public void add(WebSocketSession session){
        this.list.add(session);
    }

    // 웹소켓 세션 유저삭제
    public void remove(WebSocketSession session) {
        this.list.remove(session);
    }

    // 웹소켓 세션에 메시지 전달 (이 메시지를 통해 기능을 제어)
    public void sendMessage(String message) {
        TextMessage textMessage = new TextMessage(message);
        for(WebSocketSession session:list) {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLastSession() {
        return list.size()<=1;
    }

}

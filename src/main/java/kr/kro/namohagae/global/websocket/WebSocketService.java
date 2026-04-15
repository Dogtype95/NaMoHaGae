package kr.kro.namohagae.global.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kro.namohagae.global.dto.NotificationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Component
public class WebSocketService {
    private List<WebSocketUser> list = new Vector<>();

    // 세션이 연결되면 -> list에 사용자가 없으면 생성해서 넣고, 있으면 사용자에 세션만 추가
    public void connect(WebSocketSession session) {
        String username= session.getPrincipal().getName();
        for(WebSocketUser user:list) {
            if(user.getUsername().equals(username)) {
                user.add(session);
                return;
            }
        }
        list.add(new WebSocketUser(username,session));
    }

    //세션 끊기면 -> 사용자의 마지막 세션이면 사용자 삭제 , 아니면 세션만 삭제
    public void disconnect(WebSocketSession session) {
        String username= session.getPrincipal().getName();
        for(int i=list.size()-1; i>-1; i--) {
            if (list.get(i).getUsername().equals(username)) {
                if (list.get(i).isLastSession()) {
                    list.remove(i);
                }else {
                    list.get(i).remove(session);
                }
            }
        }
    }
    
    //알람 데이터 json으로 파싱 
    private String toJson(NotificationDto.FindAll notification) {
        Map<String, NotificationDto.FindAll> map=new HashMap<>();
        map.put("notification", notification);
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    //특정 사용자에게 알림 전송
    public void sendMessage(String memberEmail, NotificationDto.FindAll notification) {
        String json= toJson(notification);
        for(WebSocketUser user:list) {
            if(user.getUsername().equals(memberEmail)) {
                user.sendMessage(json);
            }
        }
    }
    // 모든 사용자에게 알림 전송
    public void sendAll(NotificationDto.FindAll notification) {
        String json= toJson(notification);
        for(WebSocketUser user:list) {
            user.sendMessage(json);
        }
    }
}

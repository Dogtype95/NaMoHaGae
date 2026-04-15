package kr.kro.namohagae.puching.websocket;


import kr.kro.namohagae.puching.service.ChatService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    private ChatService service;



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username= session.getPrincipal().getName();
        // WebSocketSession에서 사용자 정보를 저장합니다.
        session.getAttributes().put("username", username);
        // 저장된 사용자 정보를 출력합니다.
        sessions.put(username, session);
        // WebSocket 연결이 성공적으로 열리면 호출됩니다.

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String sendusername= session.getPrincipal().getName();
        String payload = message.getPayload().toString();

        JSONObject jsonPayload = new JSONObject(payload);
        String receiverUsername = jsonPayload.getString("receiverUsername");
        String messageContent = jsonPayload.getString("message");
        String messageType= jsonPayload.getString("messageType");

        JSONObject responseJson = new JSONObject();
        responseJson.put("sendername", sendusername);
        responseJson.put("receivername", receiverUsername);
        responseJson.put("message", messageContent);


        if(messageType.equals("text")){
         Integer haveChatRoom= service.saveTextMessage(sendusername,receiverUsername,messageContent,"text"); //메세지 저장 실패하면 리턴값이
            responseJson.put("haveChatRoom",haveChatRoom);
        }
        if(messageType.equals("image") || messageType.equals("puching")){
           Integer haveChatRoom= service.existByChatRoom(sendusername,receiverUsername);
            responseJson.put("haveChatRoom",haveChatRoom);
        }


        if (receiverUsername != null) {
            // 수신자가 지정된 경우, 수신자에게만 메시지를 전송합니다.
            WebSocketSession receiverSession = sessions.get(receiverUsername);

            if (receiverSession != null && receiverSession.isOpen()) {
                TextMessage textMessage = new TextMessage(responseJson.toString());
                receiverSession.sendMessage(textMessage);
            }
        } else {
            // 수신자가 지정되지 않은 경우, 메시지를 전송하지 않음. 여기에 메세지전송 실패를 리턴해줘서 채팅창에 찍자
        }



    }



    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // WebSocket 통신 중 에러가 발생하면 호출
        System.out.println("웹소켓이 에러");

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String username= session.getPrincipal().getName();
        sessions.remove(username);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}

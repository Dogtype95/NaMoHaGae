package kr.kro.namohagae.puching.websocket;

import kr.kro.namohagae.puching.service.ChatService;
import kr.kro.namohagae.puching.service.PuchingService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *  작성자 : 김현욱
 * 
 * * */

//채팅웹소켓 핸들러
//웹소켓 서비스의 실제로직을 핸들러에 작성 후 해당 서비스가 필요한 클래스에 빈을 주입하여 사용
@Component
public class ChatPuchingWebsocketHandler implements WebSocketHandler{ // 웹소켓 핸들러 인터페이스 

    @Autowired
    private ChatService chatService;
    @Autowired
    private PuchingService puchingService;

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    //웹소켓 연결 시 웹소켓 유저 세션에 기존 멤버세션값 세팅 (동기화)
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username= session.getPrincipal().getName(); // 웹소켓 세션 유저값
        session.getAttributes().put("username", username); // user값 세팅
        sessions.put(username,session);
    }

    // 메시지 전달
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        // 메시지 발신자 정보 get
        String sendusername= session.getPrincipal().getName();
        String payload = message.getPayload().toString();

        //메시지 내용 파싱
        JSONObject jsonPayload = new JSONObject(payload);
        String receiverUsername = jsonPayload.getString("receiverUsername");
        String messageContent = jsonPayload.getString("message");

        //json으로 response 값 세팅
        JSONObject responseJson = new JSONObject();
        responseJson.put("sendername", sendusername);
        responseJson.put("receivername", receiverUsername);
        responseJson.put("message", messageContent);

        // 취소 메시지 여부 확인
        if(messageContent.equals("cancel")){
            String content="<div class=\"puching-info\">\n" +
                    "<span class=\"puching-title\">퍼칭이 취소 되었습니다</span>\n" +
                    "</div>";
        //퍼칭 서비스로 상대번호와 내번호로 신청상태인 퍼칭 테이블을 조회하고 있으면 취소상태로 바꾸고 메세지번호를 리턴
           chatService.cancelPuchingMessage(sendusername,receiverUsername,content); //메세지 내용을 취소되었습니다 로 바꾸는거
        }

        if(messageContent.equals("accept")){
            chatService.aceeptPuchingMessage(sendusername,receiverUsername);
            //퍼칭 서비스로 상대번호와 내번호로 신청상태인 퍼칭테이블을 조회하고 있으면 수락상태로 바꾸고 퍼칭번호를 리턴 리뷰작성하러가기 페이지에는 모델에 퍼칭테이블 내용을 담아서쏜다
            //서브쿼리
        }


        if (receiverUsername != null) {
            // 수신자가 지정된 경우, 수신자에게만 메시지를 전송합니다.
            WebSocketSession receiverSession = sessions.get(receiverUsername);

            if (receiverSession != null && receiverSession.isOpen()) {
                TextMessage textMessage = new TextMessage(responseJson.toString());
                receiverSession.sendMessage(textMessage);
            }
        } else {
            // 수신자가 지정되지 않은 경우, 메시지를 전송하지 않습니다. 여기에 메세지전송 실패를 리턴해줘서 채팅창에 찍자
        }

    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println(exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      String username= session.getPrincipal().getName();
        sessions.remove(username);

    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}

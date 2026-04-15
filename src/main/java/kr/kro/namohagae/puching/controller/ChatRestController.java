package kr.kro.namohagae.puching.controller;



import kr.kro.namohagae.puching.dto.ChatRoomDto;
import kr.kro.namohagae.puching.dto.MessageDto;
import kr.kro.namohagae.puching.entity.Message;
import kr.kro.namohagae.puching.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

// 채팅 컨트롤러
@RestController
@RequestMapping("/api/v1")
public class ChatRestController {
    @Autowired
    private ChatService service;

    // 사용자채팅로그 Get
    @GetMapping(value="/findchatlog")
    public ResponseEntity<List<MessageDto.MessageRead>> findByChatLog(Principal principal,Integer receiverNo) {
         String senderEmail= principal.getName();
        return ResponseEntity.ok().body(service.findMessageLog(senderEmail,receiverNo));
    }

    //사용자 채팅방 정보 Get
    @GetMapping(value="/findchatroom")
    public ResponseEntity<ChatRoomDto.Read> findChatRoomByReceiverNo(Principal principal,String receiverEmail){
        return ResponseEntity.ok().body(service.findChatRoom(principal.getName(),receiverEmail));
    }

    // 사용자 채팅방 나가기(DB상엔 데이터 남김)
    @GetMapping(value = "/existchatroom")
    public ResponseEntity<ChatRoomDto.Read> existChatroom(Principal principal,String receiverEmail){
        return ResponseEntity.ok().body(service.existchatRoom(principal.getName(),receiverEmail));
    }

    // 채팅방 이미지 저장하기
    @PostMapping(value = "/savechatimage")
    public ResponseEntity<Message> saveImage(MultipartFile file, Principal principal, Integer receiverNo){
      String userEmail=principal.getName(); // Principal에서 username 추출
       Message message= service.saveImage(file,userEmail,receiverNo); // 이미지 객체 생성

        return ResponseEntity.ok().body(message); // 이미지 객체 return
    };

    // 퍼칭 신청하기
    @PostMapping(value = "/sendpuching")
    public ResponseEntity<Message> savePuchingMessage(Principal principal,MessageDto.PuchingMessageSave dto){
        return ResponseEntity.ok().body(service.savePuchingMessage(dto,principal.getName()));
    };




}

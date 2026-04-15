package kr.kro.namohagae.puching.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


//채팅 Entity
@Getter
@AllArgsConstructor
@ToString
@Builder
public class ChatRoom {
    private Integer chatRoomNo; //채팅번호
    private Integer memberNo; // 멤버번호
    private Integer chatRoomReceiverNo; // 채팅 발신자 번호



}

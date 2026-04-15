package kr.kro.namohagae.puching.dto;

import lombok.Data;

public class ChatRoomDto {

    @Data
    public static class Read{
        private Integer chatRoomReceiverNo; // 채팅 발신자 번호
        private String memberNickName; // 유저 닉네임
        private String memberImage; // 유저 프로필 이미지
        private String memberEmail; // 유저 이메일
    }



}

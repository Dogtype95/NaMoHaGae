package kr.kro.namohagae.puching.dto;

import lombok.Data;

// 퍼칭 Dto
public class PuchingDto {

    @Data
    public static class readTown{
        private Integer townNo; // 동네 번호
        private String townDong; // 동네
        private Double townLatitude; // 동네 위도
        private Double townLongitude; // 동네 경도
        private Integer townCnt; // 동네 수
    };

    @Data
    public static class readUser{
        private Integer memberNo; // 멤버 no
        private String memberNickName; // 멤버 닉네임
        private Double memberLatitude; // 멤버 현재 위도
        private Double memberLongitude; // 멤버 현재 경도
        private Double distance; // 다른 유저와의 거리
        private String memberImage; // 멤버 프로필 이미지
    }



};

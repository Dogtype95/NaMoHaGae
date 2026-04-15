package kr.kro.namohagae.puching.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.kro.namohagae.puching.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

// 리뷰 DTo
public class ReviewDto {

    @Data 
    public static class profile {
        private String reviewWriter; // 리뷰작성자
        private String reviewContent; // 리뷰내용
        private Integer reviewStar; // 리뷰 별점
    }

    @Data
    public static class Information {
        private Integer receiverNo; //발신자 no
        private String reviewReceiver; // 리뷰 발신자이름
        private String reviewContent; // 리뷰내용
        @JsonFormat(pattern = "yy년 MM월 dd일 hh:mm:ss")
        private LocalDateTime puchingPromiseDate; // 퍼칭 약속 날짜 데이터
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class PaginationProfie {
        private Integer pageno; // 페이지 번호
        private Integer prev; // 이전 
        private Integer start; // 시작
        private Integer end; // 마지막
        private Integer next; //다음
        private List<ReviewDto.profile> review;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Writeview {
        private String myImage; // 유저가 첨부한 이미지
        private String receiverImage; // 발신자 이미지
        private String myNickName; // 수신자user 닉네임
        private String receiverNickName; //발신 user 닉네임
        private Double latitude; //퍼칭 장소 위도
        private Double longitude; //퍼칭 장소 경도
        private String promiseDate; // 퍼칭 약속날짜

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Write {
        private Integer receiverNo; // 발신자 번호
        private Integer puchingNo; // 퍼칭 번호
        private String content; // 퍼칭내용
        private Integer starPoint; // 시작지점

        //리뷰 entity로 변환
        public Review toEntity(Integer senderNo) {

            return Review.builder().puchingNo(puchingNo).puchingReviewContent(content).puchingReviewReceiver(receiverNo)
                    .puchingReviewWriter(senderNo).puchingReviewWriteDate(LocalDateTime.now()).puchingReviewStar((starPoint * 2) - 6).build();
        }

    }
    //페이지 네이션 정보 Dto
    @ToString
    @AllArgsConstructor
    @Data
    public static class PaginationInfo {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<ReviewDto.Information> review;
    }
}

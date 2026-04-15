package kr.kro.namohagae.puching.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Review {
    private Integer puchingReviewNo; // 퍼칭 리뷰번호
    private Integer puchingNo; // 퍼칭 번호
    private Integer puchingReviewWriter; // 퍼칭 리뷰작성자
    private Integer puchingReviewReceiver; // 퍼칭 리뷰 수신자
    private String puchingReviewContent; // 퍼칭 리뷰내용
    private Integer puchingReviewStar; // 리뷰별정
    private LocalDateTime puchingReviewWriteDate; // 리뷰 작성일자
}

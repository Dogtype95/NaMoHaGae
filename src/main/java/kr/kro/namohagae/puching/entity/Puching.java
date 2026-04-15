package kr.kro.namohagae.puching.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

//퍼칭 정보 Entity
@Getter
@AllArgsConstructor
@ToString
@Builder
public class Puching {

    private Integer puchingNo; // 퍼칭번호
    private Integer messageNo; // 메시지번호(퍼칭 신청시 생성된 메시지 번호)
    private LocalDateTime puchingCreatedDate; // 생성날짜
    private String puchingStatus; // 퍼칭상태
    private LocalDateTime puchingPromiseDate; //퍼칭 약속날짜
    private Double puchingLatitude; // 퍼칭 약속장소(위도)
    private Double puchingLongitude; // 퍼칭 약속장소(경도)
    private LocalDateTime puchingApplyDate; // 퍼칭 수락 시간
    private LocalDateTime puchingDeadlineDate; // 퍼칭 기한 (기한이 지날시 스케쥴러에서 퍼칭 거절로 처리)

}

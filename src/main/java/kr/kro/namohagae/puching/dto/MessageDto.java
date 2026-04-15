package kr.kro.namohagae.puching.dto;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.puching.entity.Message;
import kr.kro.namohagae.puching.entity.Puching;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// message 디티오
public class MessageDto {

    // 메시지 기본 Dto
    @Data
    public static class MessageRead{
        private Integer messageSenderNo; // 발신 user No
        private Integer messageReceiverNo; // 수신 user No
        private String messageContent; // 메시지 내용
        private String messageContentType; // 메시지 타입
        private String messageWriteDate; // 메시지 작성 날짜
    }

    // Text 메시지 Dto
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageSave{
        private Integer messageSender; // 발신자 No
        private Integer messageReceiver;  // 수신자 NO
        private String messageContent; // 메시지 내용

        // Message Entity 객체로 변환
        public Message toEntity(String messageContentType) {
            return Message.builder().messageSender(messageSender).messageReceiver(messageReceiver).messageContent(messageContent)
                    .messageContentType(messageContentType).messageWriteDate(LocalDateTime.now()).build(); 
        }
    }

    // 이미지 메시지 Dto
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageMessageSave{
        private Integer messageSender; // 발신자 No
        private Integer messageReceiver; // 수신자 No


        // Message Entity 객체로 변환(이미지 데이터 포함)
        public Message toEntity(String messageContentType,String imageName){
            return Message.builder().messageSender(messageSender).messageReceiver(messageReceiver)
                    .messageContent("<img src='"+ ImageConstants.IMAGE_CHAT_URL +imageName+"'alt='chatimage'>")
                    .messageContentType(messageContentType).messageWriteDate(LocalDateTime.now()).build();
        }
    }


    //퍼칭 메시지 Dto
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PuchingMessageSave{
        private String receiverUsername; // 발신자 No
        private Double lat; //위도
        private Double lng; // 경도
        private String day; // 날짜
        private String time; // 시간
        private String address; // 주소
        // 퍼칭 메시지 생성자
        public Message toEntity(Integer senderNo,Integer receiverNo,String messageContent){

            return Message.builder().messageSender(senderNo).messageReceiver(receiverNo).messageContent(messageContent)
                    .messageContentType("puching").messageWriteDate(LocalDateTime.now()).build();
        };
        //퍼칭 데이터 생성
        public Puching toEntity(Integer messageNo){
            String dateTimeStr = day + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime puchingPromiseDate = LocalDateTime.parse(dateTimeStr, formatter);
            return Puching.builder().messageNo(messageNo).puchingLatitude(lat).puchingLongitude(lng).puchingStatus("신청")
                    .puchingApplyDate(LocalDateTime.now()).puchingCreatedDate(LocalDateTime.now()).puchingDeadlineDate(LocalDateTime.now())
                    .puchingPromiseDate(puchingPromiseDate).build();
        };
    }

}

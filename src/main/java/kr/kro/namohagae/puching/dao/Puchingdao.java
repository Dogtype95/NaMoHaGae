package kr.kro.namohagae.puching.dao;

import kr.kro.namohagae.puching.dto.PuchingDto;
import kr.kro.namohagae.puching.entity.Puching;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 퍼칭 기능 Dao
@Mapper
public interface Puchingdao {


    @Select("select t.town_no as townNo,t.town_dong as townDong,t.town_latitude as townLatitude," +
            "t.town_longitude as townLongitude,count(m.town_no) as townCnt " +
            "from TOWN t,MEMBER m   where t.TOWN_NO=m.TOWN_NO(+) " +
            "group by t.town_no,t.town_dong,t.town_latitude,t.town_longitude,m.town_no order by townNO")
    public List<PuchingDto.readTown> findAllTown();
    public List<PuchingDto.readUser> findByUsers(String url, Double latitude,Double longitude,Integer startrownum,Integer endrownum,Integer memberNo);


    public Integer checkPuching(Integer senderNo,Integer receiverNo);  //퍼칭 체크

    public void savePuching(Puching puching); //퍼칭 저장

    public void updatePuchingStatus(Integer messageNo,String puchingStatus);//퍼칭 상태변경

    public Integer checkWritePuchingReviewBysenderNo(Integer senderNo,Integer messageNo); //퍼칭 신청 상대 체크

    public String checkPuchingStatus(Integer messageNo); //퍼칭상태 정보 Get

    public Integer findPuchingNoBySenderNoAndReceiverNo(Integer senderNo,Integer receiverNo); // 퍼칭번호로 보낸사람과 받은사람 No Get
}

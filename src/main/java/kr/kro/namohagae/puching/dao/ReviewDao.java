package kr.kro.namohagae.puching.dao;

import kr.kro.namohagae.puching.dto.ReviewDto;
import kr.kro.namohagae.puching.entity.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 퍼칭 리뷰 매퍼
@Mapper
public interface ReviewDao {

    public List<ReviewDto.profile> findContentByReceiverNo(Integer startRownum, Integer endRownum,Integer memberNo); //  수신자 No찾기
    public List<ReviewDto.Information>findContentByWriterNo(Integer startRownum, Integer endRownum,Integer memberNo);// 작성자 No 찾기
    public Integer countReceiver(Integer memberNo); // 총 퍼칭신청  count
    public Integer countWriter(Integer memberNo);  // 퍼칭 신청중인 사람 count
    public Integer count(Integer receiverNo); // 퍼칭 보낸 사람 count

    public ReviewDto.Writeview reviewWriteInfo(String url, Integer senderNo, Integer receiverNo, Integer puchingNo); // 리뷰 작성 정보 Get

    public void save(Review review); // 퍼칭 저장

    public Integer checkCountReviewByPuchingNo(Integer puchingNo); // 퍼칭No로 리뷰 수 체크

    public void updateMemberPoint(Integer bonePoint,Integer senderNo); // 멤버 포인트 변경

    public void updateMemberGrade(Integer starPoint,Integer receiverNo); // 멤버 등급 변경

    public void updatePuchingStatus(Integer puchingNo); // 퍼칭 진행상태코드 변경

    public void updateMessageContentType(Integer puchingNo); //메시지 콘텐츠 타입 변경
}

package kr.kro.namohagae.puching.service;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puching.dao.ChatDao;
import kr.kro.namohagae.puching.dao.Puchingdao;
import kr.kro.namohagae.puching.dao.ReviewDao;
import kr.kro.namohagae.puching.dto.ReviewDto;
import kr.kro.namohagae.puching.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 리뷰서비스
@Service
public class ReviewService {

    // 리뷰 관련 빈 주입
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private Puchingdao puchingdao;

    //리뷰 페이지 상수 생성
    private final static Integer PAGESIZE=5;
    private final static Integer BLOCKSIZE=3;

    //프로필 페이지 네이션
    public ReviewDto.PaginationProfie findContentByReceiverNo(Integer pageno,Integer memberNo) {
        Integer countOfProduct = reviewDao.countReceiver(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno); 
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1; // 시작 로우넘
        Integer endRownum = startRownum + PAGESIZE - 1; // 마지막 로우넘
        List<ReviewDto.profile> review = reviewDao.findContentByReceiverNo(startRownum, endRownum,memberNo); // 내용찾기
        // 리스트 log로 찍기
        
        // 페이징
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1; 

        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        
        return new ReviewDto.PaginationProfie(pageno, prev, start, end, next, review);
    }

    // 페이징 정보 : 글번호로 내용 찾기
    public ReviewDto.PaginationInfo findContentByWriterNo(Integer pageno,Integer memberNo) {
        Integer countOfProduct = reviewDao.countWriter(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;

        List<ReviewDto.Information> review = reviewDao.findContentByWriterNo(startRownum, endRownum,memberNo); //리뷰 번호로 내용 get
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ReviewDto.PaginationInfo(pageno, prev, start, end, next, review);
    }

    // 리뷰데이터 get
    public ReviewDto.Writeview findWriteViewInfo(String userEmail, Integer receiverNo, Integer puchingNo){
        Integer userNo=memberDao.findNoByUsername(userEmail);
        ReviewDto.Writeview dto = reviewDao.reviewWriteInfo(ImageConstants.IMAGE_PROFILE_URL,userNo,receiverNo,puchingNo);

        return dto;
    }

    // 리뷰 저장
    @Transactional
    public void saveReview(String username,ReviewDto.Write dto){
        Integer memberNo=memberDao.findNoByUsername(username);
        Review review= dto.toEntity(memberNo);
        Integer bonePoint=300;
            reviewDao.save(review);
            reviewDao.updateMemberPoint(bonePoint,memberNo);
            reviewDao.updateMemberGrade(dto.getStarPoint(),dto.getReceiverNo());
        if(reviewDao.checkCountReviewByPuchingNo(dto.getPuchingNo())>=2) {  //   퍼칭번호로 작성된 리뷰글 숫자 체크 이거의 여부에 따라 리뷰작성후 상태랑 콘텐트타입을 바꾼다
            reviewDao.updatePuchingStatus(dto.getPuchingNo());
            reviewDao.updateMessageContentType(dto.getPuchingNo());
        }

    }
}

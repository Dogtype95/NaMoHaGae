package kr.kro.namohagae.puching.controller;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puching.dto.ReviewDto;
import kr.kro.namohagae.puching.service.ChatService;
import kr.kro.namohagae.puching.service.PuchingService;
import kr.kro.namohagae.puching.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

//퍼칭 컨트롤러
@Controller
@RequiredArgsConstructor
public class PuchingController {

    // 퍼칭관련 bean 주입
    private final MemberDao memberDao;
    private final ChatService chatService;
    private final ReviewService puchingReviewService;

    
    @Secured("ROLE_DOG") // 프로필에 강아지를 등록한 사람만 가능하도록 sucured 설정
    @GetMapping("/puching/chatroom") // 퍼칭화면에서 -> 채팅룸 이동 객체에 따라 채팅화면의 default 설정이 바뀜
    public String chatroom(Principal principal, Model model, @RequestParam(defaultValue = "")String receiverEmail) {
        if(!receiverEmail.equals("")){
            Boolean existByEmail=memberDao.existsByEmail(receiverEmail);
            if(principal.getName().equals(receiverEmail) || !existByEmail) {
                model.addAttribute("msg","채팅상대를 못찾았습니다.");
            }
            if(existByEmail && !principal.getName().equals(receiverEmail)) {
                model.addAttribute("startuser", receiverEmail);
            }
        }

        Integer myMemberNo=memberDao.findNoByUsername(principal.getName());
        model.addAttribute("list",chatService.findAllChatRoom(myMemberNo));
        model.addAttribute("mymemberNo",myMemberNo);

        return "puching/chatRoom";
    }
    @Secured("ROLE_DOG")// 프로필에 강아지를 등록한 사람만 가능하도록 sucured 설정
    @GetMapping("/puching/locationview") // 위도(lat), 경도(lng) 위치에 해당하는 html팝업 생성하기
    public String locationview(@RequestParam("lat")Double lat,@RequestParam("lng") Double lng){

        return "puching/locationview";
    };
    @Secured("ROLE_DOG")// 프로필에 강아지를 등록한 사람만 가능하도록 sucured 설정
    @GetMapping("/puching/reviewwrite") // 퍼칭 리뷰 작성하기 페이지 이동
    public String reviewwrite(@RequestParam("receiverNo")Integer receiverNo,@RequestParam("puchingNo")Integer puchingNo,Principal principal,Model model){
        ReviewDto.Writeview dto=puchingReviewService.findWriteViewInfo(principal.getName(),receiverNo,puchingNo);
        model.addAttribute("list",dto);
        return "puching/reviewwrite";
    }
    @Secured("ROLE_DOG")// 프로필에 강아지를 등록한 사람만 가능하도록 sucured 설정
    @PostMapping(value="/puching/reviewwrite") // 퍼칭 리뷰 작성
    public String write(ReviewDto.Write dto, Principal principal) {
        puchingReviewService.saveReview(principal.getName(),dto);
        return "redirect:/puching/main";
    }
    @Secured("ROLE_DOG")// 프로필에 강아지를 등록한 사람만 가능하도록 sucured 설정
    @GetMapping(value = "/puching/puching_introduce") // 퍼칭 소개 페이지
    public String introduce(){
        return "puching/puching_introduce";
    }

    @Secured("ROLE_DOG")// 프로필에 강아지를 등록한 사람만 가능하도록 sucured 설정
    @GetMapping(value = "/puching/search") // 유저Id 검색 페이지로 이동
    public String search(){
        return "puching/search";
    }

}

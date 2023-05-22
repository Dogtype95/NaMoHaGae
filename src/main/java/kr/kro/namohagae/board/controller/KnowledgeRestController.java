package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.KnowledgeAnswerDto;
import kr.kro.namohagae.board.service.KnowledgeService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class KnowledgeRestController {
    private final KnowledgeService knowledgeService;
    private final MemberService memberService;

    @PostMapping("/knowledge/answer/write")
    public ResponseEntity<String> answerWrite(KnowledgeAnswerDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails){
        Boolean result = knowledgeService.answerSave(dto, myUserDetails.getMemberNo());
        if (result) {
           return ResponseEntity.ok("성공");
        }else{
           return ResponseEntity.status(HttpStatus.CONFLICT).body("실패");
        }
    }

    @GetMapping("/knowledge/answer/list")
    public ResponseEntity<List<KnowledgeAnswerDto.Read>>answerList(Integer questionNo){
        return ResponseEntity.ok(knowledgeService.answerFindAll(questionNo));
    }

    @PostMapping("/knowledge/answer/select")
    public ResponseEntity<String> answerSelect(Integer answerNo){
        Boolean result = knowledgeService.answerUpdate(answerNo);
        if (result) {
            return ResponseEntity.ok("성공");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("실패");
        }
    }

    @GetMapping("/member/knowledge/question")
    public ResponseEntity<?> myQuestionList(Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo = myUserDetails.getMemberNo();
        return  ResponseEntity.ok(knowledgeService.myQusetionList(pageno,memberNo));
    }
}

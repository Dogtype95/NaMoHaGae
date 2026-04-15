package kr.kro.namohagae.puching.controller;

import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import kr.kro.namohagae.puching.dto.PuchingDto;
import kr.kro.namohagae.puching.service.PuchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// 퍼칭 Rest 컨트롤러 
@Controller
@RequestMapping("/api/v1")
public class PuchingRestController {
    //퍼칭 빈 주입
    @Autowired
    private PuchingService service;

    // 동네 리스트 데이터 Get
    @GetMapping(value="/puching/townlist")
    public ResponseEntity<List<PuchingDto.readTown>> townlist(){
        List<PuchingDto.readTown> list = service.findAllTown();
        return ResponseEntity.ok().body(list);
    }

    // 유저리스트 데이터 Get
    @GetMapping(value="/puching/userlist")
    public ResponseEntity<List<PuchingDto.readUser>> userList(Double latitude, Double longitude, Integer pageNum, Integer pageSize, Principal principal) { //들어온 숫자 검증
        String userEmail=principal.getName();
        return ResponseEntity.ok().body(service.readUsers(latitude,longitude,pageNum,pageSize,userEmail));
    }

    // 선택한 유저 퍼칭 상태인지 체크
    @GetMapping(value="/puching/checkpuching")
    public ResponseEntity<Void> checkpuching(Principal principal,Integer receiverNo){
      Integer result = service.checkpuching(principal.getName(),receiverNo); //  receiverNo가 있을 경우 퍼칭 상태로 간주
      if (result==0){
          return ResponseEntity.ok().body(null);
      }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 퍼칭 상태일 경우 CONFLICT Exception Handler 처리
    };

    // 진행중인 퍼칭리뷰가 작성완료된 상태인지 체크( 이중입력 방지)
    @GetMapping(value = "/puching/checkreviewpuching")
    public ResponseEntity<Map<String,Integer>> checkPuchingReviewWrite(Principal principal, Integer receiverNo){
        Integer result=service.checkAcceptPuching(principal.getName(),receiverNo);
            Map<String,Integer> map=new ConcurrentHashMap<>();
        if(result==0) {
            Integer puchingNo=service.findPuchingNo(principal.getName(),receiverNo);
            map.put("puchingNo",puchingNo);
            return ResponseEntity.ok().body(map);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    // 유저 Id로 검색하기
    @GetMapping(value = "/puching/searchuser")
    public ResponseEntity<MemberDto.Read> searchuser(String usernick){
            Optional<Member> member= service.findMember(usernick);
        if(member==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
          MemberDto.Read dto=  member.get().toReadDto();
        return ResponseEntity.ok().body(dto);
    }
}
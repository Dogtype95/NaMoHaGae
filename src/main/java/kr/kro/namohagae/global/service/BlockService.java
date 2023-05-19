package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.BlockDao;
import kr.kro.namohagae.global.dao.ReportDao;
import kr.kro.namohagae.global.dto.BlockDto;
import kr.kro.namohagae.global.entity.Block;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BlockService {
    @Autowired
    private BlockDao blockDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ReportDao reportDao;

    public Boolean save(BlockDto.save dto) {
        String dateString = dto.getBlockDeadlineDate();
        String pattern = "yyyy-MM-dd";
        LocalDateTime dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(pattern));
        Block block = dto.toEntity(dateTime);
        memberDao.memberEnabled(dto.getMemberNo(),false);
        return blockDao.save(block);
    }
    public Boolean delete(LocalDateTime today){
        List<Integer> leavePrisons = blockDao.findMemberNoByToday(today);

        for (Integer i: leavePrisons) {
            Integer result = blockDao.findReportByMemberNo(i);
            memberDao.memberEnabled(i,true);
            reportDao.delete(result);
        }
        return blockDao.delete(today);
    }

}

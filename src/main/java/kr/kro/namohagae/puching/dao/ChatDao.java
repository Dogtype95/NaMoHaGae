package kr.kro.namohagae.puching.dao;

import kr.kro.namohagae.puching.dto.ChatRoomDto;
import kr.kro.namohagae.puching.dto.MessageDto;
import kr.kro.namohagae.puching.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 채팅 Dao 매퍼
@Mapper
public interface ChatDao {


    public Boolean existsByChatRoom(Integer memberNo,Integer receiverNo);  //상대방과의 채팅방이 있는지 체크
    public void saveChatRoom(Integer senderNo,Integer receiverNo); // 첫 메세지 저장시 채팅방 생성

    public List<ChatRoomDto.Read> findAllChatRoom(String url, Integer memberNo); // 자신이 가지고 있는 채팅방

    public ChatRoomDto.Read findChatRoom(String url, Integer memberNo,Integer receiverNo);// 내번호와 상대번호로 채팅방 정보 불러오기

    public ChatRoomDto.Read findChatRoomByReceiverNo(String url, Integer receiverNo);

    public Integer saveMessage(Message message); // 메세지 전송 성공시 저장

    public List<MessageDto.MessageRead> findAllMessageByReceiverNo(Integer senderNo,Integer receiverNo); // 상대방과의 채팅 기록 불러오기

    public void saveImage(Message message); // 이미지 저장후 messagedata리턴

    public Integer savePuchingMessage(Message message); //메세지 번호를 리턴

    public Integer findPuchingMessageNo(Integer senderNo,Integer receiverNo,String contentType);   //매퍼작성

    public void updatePuchingMessage(Integer messageNo,String contentType,String content);

    public void replacePuchingMessage(Integer messageNo,String removeString,String addString);

}

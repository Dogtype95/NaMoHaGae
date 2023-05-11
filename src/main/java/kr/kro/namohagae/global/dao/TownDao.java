package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.dto.TownDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TownDao {

    @Select("select town_no from town where town_dong=#{townDong}")
    public Integer findNoByDong(String townDong);
    @Select("select town_gu from town")
    public List<TownDto.Gu> findGu();

    @Select("select town_no, town_dong from town where town_gu=#{townGu}")
    public List<TownDto.Read> findTownDongByGu(String townGu);
}

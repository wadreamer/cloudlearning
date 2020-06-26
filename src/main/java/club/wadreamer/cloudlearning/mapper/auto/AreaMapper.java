package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.Area;

import java.util.List;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

    List<Area> queryAllAreaByCityId(int cityId);
}
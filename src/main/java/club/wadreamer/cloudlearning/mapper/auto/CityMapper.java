package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.City;

import java.util.List;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    List<City> queryAllCityByProvinceId(int provinceId);
}
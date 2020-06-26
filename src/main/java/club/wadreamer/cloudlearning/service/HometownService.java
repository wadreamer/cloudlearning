package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.mapper.auto.AreaMapper;
import club.wadreamer.cloudlearning.mapper.auto.CityMapper;
import club.wadreamer.cloudlearning.mapper.auto.ProvinceMapper;
import club.wadreamer.cloudlearning.model.auto.Area;
import club.wadreamer.cloudlearning.model.auto.City;
import club.wadreamer.cloudlearning.model.auto.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProvinceService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/13 20:08
 * @Version 1.0
 **/
@Service
public class HometownService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private AreaMapper areaMapper;

    public List<Province> getAllProvince(){
        return provinceMapper.queryAllProvince();
    }

    public List<City> getAllCity(int provinceId){
        return cityMapper.queryAllCityByProvinceId(provinceId);
    }

    public List<Area> getAllArea(int cityId){
        return areaMapper.queryAllAreaByCityId(cityId);
    }

}

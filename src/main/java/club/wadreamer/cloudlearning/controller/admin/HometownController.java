package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Area;
import club.wadreamer.cloudlearning.model.auto.City;
import club.wadreamer.cloudlearning.model.auto.Province;
import club.wadreamer.cloudlearning.service.HometownService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName HometownController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/13 20:19
 * @Version 1.0
 **/
@Api(value = "省市区三级联动")
@Controller
@RequestMapping("/HometownController")
public class HometownController {
    @Autowired
    private HometownService homeTownService;

    @GetMapping("/getProvinces")
    @ResponseBody
    public Object getProvinces(){
        List<Province> list = homeTownService.getAllProvince();
        return AjaxResult.success(list);
    }

    @PostMapping("/getCities")
    @ResponseBody
    public Object getCities(int provinceId){
        List<City> list = homeTownService.getAllCity(provinceId);
        return AjaxResult.success(list);
    }

    @PostMapping("/getAreas")
    @ResponseBody
    public Object getAreas(int cityId){
        List<Area> list = homeTownService.getAllArea(cityId);
        return AjaxResult.success(list);
    }
}

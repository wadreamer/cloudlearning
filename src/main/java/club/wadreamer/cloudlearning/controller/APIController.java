package club.wadreamer.cloudlearning.controller;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.custom.CourseSelectedDao;
import club.wadreamer.cloudlearning.mapper.custom.PermissionDao;
import club.wadreamer.cloudlearning.model.auto.Area;
import club.wadreamer.cloudlearning.model.auto.City;
import club.wadreamer.cloudlearning.model.auto.Permission;
import club.wadreamer.cloudlearning.model.auto.Province;
//import club.wadreamer.cloudlearning.model.custom.UserInfo;
//import club.wadreamer.cloudlearning.service.HomeTownService;
import club.wadreamer.cloudlearning.model.custom.BootstrapTree;
import club.wadreamer.cloudlearning.model.custom.CourseReviewLog;
import club.wadreamer.cloudlearning.service.CourseSelectedService;
import club.wadreamer.cloudlearning.service.PermissionService;
import club.wadreamer.cloudlearning.service.ReviewLogService;
import club.wadreamer.cloudlearning.service.UserService;
import club.wadreamer.cloudlearning.util.JsonUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName APIController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/3 15:43
 * @Version 1.0
 **/
@Controller()
@RequestMapping("API")
public class APIController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private CourseSelectedService courseSelectedService;

    @Autowired
    private ReviewLogService reviewLogService;

    @ResponseBody
    @GetMapping("/getJsonData")
    public Object getJsonData(String userId) {
        BootstrapTree bootstrapTree = permissionService.getbooBootstrapTreePerm(userId);
        return AjaxResult.successData(200, bootstrapTree);
    }

    @GetMapping("/data")
    @ResponseBody
    public Object getPhotos(int cid){
        return courseSelectedService.getCourseAllChapterWithVideo(cid);
    }

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private HomeTownService homeTownService;
//
//    @GetMapping("/getUserInfo")
//    @ResponseBody
//    public Object getUserInfo() {
//        PageInfo<UserInfo> pageInfo = userService.getAll(1, 3,1);
//        return AjaxResult.success(pageInfo);
//    }
//
//    @GetMapping("/getProvinces")
//    @ResponseBody
//    public Object getProvinces() {
//        List<Province> list = homeTownService.getAllProvinces();
//        return AjaxResult.success(list);
//    }
//
//    @GetMapping("/getCities")
//    @ResponseBody
//    public Object getCities(int provinceId) {
//        List<City> list = homeTownService.getAllCities(provinceId);
//        return AjaxResult.success(list);
//    }
//
//    @GetMapping("/getAreas")
//    @ResponseBody
//    public Object getAreas(int cityId) {
//        List<Area> list = homeTownService.getAllArea(cityId);
//        return AjaxResult.success(list);
//    }
}

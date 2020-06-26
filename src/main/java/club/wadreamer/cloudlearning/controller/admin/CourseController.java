package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.base.BaseController;
import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.CourseSt;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.service.CourseService;
import club.wadreamer.cloudlearning.service.CourseStService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName CourseController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/15 10:55
 * @Version 1.0
 **/
@Api(value = "课程列表")
@Controller
@RequestMapping("/CourseController")
public class CourseController extends BaseController {

    private String prefix = "admin/course";

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseStService courseStService;

    //    @RequiresPermissions("course:view")
    @GetMapping("/course_view")
    public String courseListView() {
        return prefix + "/course_list";
    }

    @GetMapping("/course_list")
    @ResponseBody
    public Object courseList(int page, int limit) {
        PageInfo<Course> list = courseService.getCourseListByUserId(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //@RequiresPermissions("course:add_view")
    @GetMapping("/course_add_view")
    public String courseAddView(ModelMap modelMap) {
        List<CourseSt> courseSts = courseStService.getAllCateSt();
        modelMap.addAttribute("courseSts", courseSts);
        return prefix + "/add";
    }

    @PostMapping("/course_add")
    @ResponseBody
    public Object courseAdd(MultipartFile image, Course course) {
        return courseService.insertCourse(image, course);
    }

    @GetMapping("/course_edit_view")
    public String courseEditView(ModelMap modelMap, int cid) {
        Course course = courseService.getCourseByKey(cid);
        List<CourseSt> courseSts = courseStService.getAllCateSt();
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("courseSts", courseSts);
        return prefix + "/course_edit";
    }

    @PostMapping("/course_edit")
    @ResponseBody
    public Object courseEdit(MultipartFile image, Course course, boolean isUpload) {
        return courseService.updateCourse(image, course, isUpload);
    }

    @PostMapping("/course_del")
    @ResponseBody
    private Object delCourse(int cid) {
        return courseService.delCourse(cid);
    }

}

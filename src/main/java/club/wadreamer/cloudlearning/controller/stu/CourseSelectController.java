package club.wadreamer.cloudlearning.controller.stu;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.CourseSelected;
import club.wadreamer.cloudlearning.model.custom.ChapterAndVideo;
import club.wadreamer.cloudlearning.model.custom.CourseWithChapterAndVideo;
import club.wadreamer.cloudlearning.service.CourseSelectedService;
import club.wadreamer.cloudlearning.service.CourseService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.JsonUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName CouseSelectController
 * @Description TODO
 * @Author bear
 * @Date 2020/4/11 12:25
 * @Version 1.0
 **/
@Api("已开设的课程")
@Controller
@RequestMapping("/CourseSelectController")
public class CourseSelectController {

    private String prefix = "/admin/course_select";

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseSelectedService courseSelectedService;

    //    @RequiresPermissions("course:view")
    @GetMapping("/course_select_view")
    public String courseSelectView(ModelMap modelMap) {
        PageInfo<Course> list = courseService.getAllCourseForSelect(1, 6);
        modelMap.addAttribute("list", list);
        System.out.println(JsonUtils.gsonString(list));
        return prefix + "/list";
    }

    @GetMapping("/all_course_list")
    @ResponseBody
    public Object courseSelectList(int page, int limit) {
        PageInfo<Course> list = courseService.getAllCourseForSelect(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }


    @PostMapping("/course_select")
    @ResponseBody
    public Object courseSelect(CourseSelected courseSelected) {
        return courseSelectedService.insertCourseSelected(courseSelected);
    }

    //    @RequiresPermissions("course:view")
    @GetMapping("/course_selected_view")
    public String courseSelectedView() {
        return prefix + "/list_selected";
    }

    @GetMapping("/course_selected_list")
    @ResponseBody
    public Object courseSelectedList(int page, int limit) {
//        PageInfo<CourseSelected> list =
//                courseSelectedService.getCourseSelectedList(page, limit, ShiroUtils.getUserId());
        PageInfo<club.wadreamer.cloudlearning.model.custom.CourseSelected> list =
                courseSelectedService.getCourseSelectedList(page, limit, ShiroUtils.getUserId());
        return AjaxResult.success_flowData("", list.getList(), list.getPages());
    }

    @GetMapping("/course_selected_detail")
    public String courseReviewView(ModelMap modelMap, int cid) {
        Course course = courseService.getCourseByKey(cid);
        modelMap.addAttribute("course", course);
        return prefix + "/detail";
    }

    @PostMapping("/course_cancel")
    @ResponseBody
    public Object courseCancelSub(int sid) {
        return courseSelectedService.cancelSub(sid);
    }

    @GetMapping("/video_play")
    public String videoPlayView(Integer cid, ModelMap modelMap) {
        CourseWithChapterAndVideo courseWithChapterAndVideo = courseSelectedService.getCourseAllChapterWithVideo(cid);

        modelMap.addAttribute("course", courseWithChapterAndVideo);
        return prefix + "/videoplay";
    }

}

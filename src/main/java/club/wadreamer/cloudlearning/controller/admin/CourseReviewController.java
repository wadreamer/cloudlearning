package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.ReviewLog;
import club.wadreamer.cloudlearning.model.custom.CourseReviewLog;
import club.wadreamer.cloudlearning.service.CourseService;
import club.wadreamer.cloudlearning.service.ReviewLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName CourseReviewController
 * @Description TODO
 * @Author bear
 * @Date 2020/4/9 17:44
 * @Version 1.0
 **/
@Api(value = "课程审核")
@Controller
@RequestMapping("/CourseReviewController")
public class CourseReviewController {

    private String prefix = "admin/course_review";

    @Autowired
    private CourseService courseService;

    @Autowired
    private ReviewLogService reviewLogService;


    //    @RequiresPermissions("course:view")
    @GetMapping("/course_unreview_view")
    public String courseUnreviewList() {
        return prefix + "/list";
    }

    @GetMapping("/course_unreview_list")
    @ResponseBody
    public Object courseUnreviewList(int page, int limit) {
        PageInfo<Course> list = reviewLogService.getUnreviewList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //    @RequiresPermissions("course:view")
    @GetMapping("/course_unpass_view")
    public String courseUnpassList() {
        return prefix + "/unpass";
    }

    @GetMapping("/course_unpass_list")
    @ResponseBody
    public Object courseUnpassList(int page, int limit) {
        PageInfo<CourseReviewLog> list = reviewLogService.getUnpassList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //    @RequiresPermissions("course:view")
    @GetMapping("/course_pass_view")
    public String coursePassList() {
        return prefix + "/pass";
    }

    @GetMapping("/course_pass_list")
    @ResponseBody
    public Object coursePassList(int page, int limit) {
        PageInfo<CourseReviewLog> list = reviewLogService.getPassList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }


    @GetMapping("/course_review_view")
    public String courseReviewView(ModelMap modelMap, int cid) {
        Course course = courseService.getCourseByKey(cid);
        modelMap.addAttribute("course", course);
        return prefix + "/review";
    }

    @PostMapping("/course_review")
    @ResponseBody
    public Object courseReview(ReviewLog reviewLog) {
        if (reviewLogService.insertSelective(reviewLog) > 0) {
            return AjaxResult.success("审核成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    //    @RequiresPermissions("teacher:del")
    @ResponseBody
    @PostMapping("/review_log_del")
    public Object courseUnpassDel(int reviewId) {
        return reviewLogService.deleteByKey(reviewId);
    }

}

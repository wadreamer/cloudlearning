package club.wadreamer.cloudlearning.controller.teacher;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.service.CourseService;
import club.wadreamer.cloudlearning.service.NoticeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName NoticeController
 * @Description TODO
 * @Author bear
 * @Date 2020/4/10 20:18
 * @Version 1.0
 **/
@Api(value = "公告列表")
@Controller
@RequestMapping("/NoticeController")
public class NoticeController {

    private String prefix = "admin/notice";

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CourseService courseService;

    //    @RequiresPermissions("chapter:view")
    @GetMapping("/notice_view")
    public String noticeView() {
        return prefix + "/list";
    }

    @GetMapping("/notice_list")
    @ResponseBody
    public Object noticeList(int page, int limit) {
        PageInfo<Notice> list = noticeService.getNoticeList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //@RequiresPermissions("course:add_view")
    @GetMapping("/notice_publish_view")
    public String noticePublishView(ModelMap modelMap) {
        List<Course> coursePassList = courseService.getCoursePassList();
        modelMap.addAttribute("coursePassList", coursePassList);
        return prefix + "/publish";
    }

    @PostMapping("/notice_publish")
    @ResponseBody
    public Object noticePublish(Notice notice) {
        if (noticeService.insertNoticeSelective(notice) > 0) {
            return AjaxResult.success("发布成功，待审核中！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    //@RequiresPermissions("admin:detail")
    @GetMapping("/notice_detail")
    public String noticeShow(int nid, ModelMap modelMap) {
        Notice notice = noticeService.getNoticeByKey(nid);
        modelMap.addAttribute("notice", notice);
        return prefix + "/show";
    }

    @RequestMapping("/notice_edit_view")
    public String noticeEditView(int nid, ModelMap modelMap) {
        List<Course> courses = courseService.getCoursePassList();
        Notice notice = noticeService.getNoticeByKey(nid);
        modelMap.addAttribute("courses", courses);
        modelMap.addAttribute("notice", notice);
        return prefix + "/edit";
    }

    //    @RequiresPermissions("admin:edit")
    @PostMapping("/notice_edit")
    @ResponseBody
    public Object noticeEdit(Notice notice) {
        return noticeService.updateNotice(notice);
    }

    @PostMapping("/notice_del")
    @ResponseBody
    public Object deleteNotice(int nid) {
        return noticeService.deleteNotice(nid);
    }

}

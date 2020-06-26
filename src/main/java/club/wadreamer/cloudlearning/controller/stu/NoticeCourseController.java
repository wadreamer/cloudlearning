package club.wadreamer.cloudlearning.controller.stu;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Chapter;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.model.custom.NoticeCourse;
import club.wadreamer.cloudlearning.service.NoticeCourseService;
import club.wadreamer.cloudlearning.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName NoticeCourseController
 * @Description TODO
 * @Author bear
 * @Date 2020/5/2 17:25
 * @Version 1.0
 **/
@Controller
@RequestMapping("/NoticeCourseController")
public class NoticeCourseController {

    private String prefix = "/admin/notice";

    @Autowired
    private NoticeCourseService noticeCourseService;

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice_course_view")
    public String noticeCourseView() {
        return prefix + "/course";
    }

    @GetMapping("/notice_course_list")
    @ResponseBody
    public Object noticeCourseList(int page, int limit) {
        PageInfo<NoticeCourse> list = noticeCourseService.getNoticeCourseAll(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    @GetMapping("/notice_course_detail")
    public String noticeCourseDetail(int nid, int ncid, int status, ModelMap modelMap) {
        if (status == 0) {
            noticeCourseService.updateReadStatus(ncid);
        }
        Notice notice = noticeService.getNoticeByKey(nid);
        modelMap.addAttribute("notice", notice);
        return prefix + "/course_detail";
    }

    @PostMapping("/notice_course_del")
    @ResponseBody
    public Object courseNoticeDel(int ncid) {
        return noticeCourseService.courseNoticeDel(ncid);
    }
}

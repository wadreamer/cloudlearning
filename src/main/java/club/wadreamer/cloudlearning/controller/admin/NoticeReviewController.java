package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.model.auto.NoticeReview;
import club.wadreamer.cloudlearning.model.auto.ReviewLog;
import club.wadreamer.cloudlearning.service.NoticeReviewService;
import club.wadreamer.cloudlearning.service.NoticeService;
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

/**
 * @ClassName NoticeReviewController
 * @Description TODO
 * @Author bear
 * @Date 2020/4/10 22:00
 * @Version 1.0
 **/
@Api(value = "公告审核")
@Controller
@RequestMapping("/NoticeReviewController")
public class NoticeReviewController {

    private String prefix = "admin/notice_review";

    @Autowired
    private NoticeReviewService noticeReviewService;

    @Autowired
    private NoticeService noticeService;

    //    @RequiresPermissions("course:view")
    @GetMapping("/notice_unreview_view")
    public String noticeUnreviewList() {
        return prefix + "/list";
    }

    @GetMapping("/notice_unreview_list")
    @ResponseBody
    public Object noticeUnreviewList(int page, int limit) {
        PageInfo<Notice> list = noticeService.getUnreviewList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //    @RequiresPermissions("course:view")
    @GetMapping("/notice_unpass_view")
    public String noticeUnpassList() {
        return prefix + "/unpass";
    }

    @GetMapping("/notice_unpass_list")
    @ResponseBody
    public Object noticeUnpassList(int page, int limit) {
        PageInfo<Notice> list = noticeReviewService.getHasReviewList(page, limit, 1);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //    @RequiresPermissions("course:view")
    @GetMapping("/notice_pass_view")
    public String noticePassList() {
        return prefix + "/pass";
    }

    @GetMapping("/notice_pass_list")
    @ResponseBody
    public Object noticePassList(int page, int limit) {
        PageInfo<Notice> list = noticeReviewService.getHasReviewList(page, limit, 2);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    @GetMapping("/notice_review_view")
    public String noticeReviewView(ModelMap modelMap, int nid) {
        Notice notice = noticeService.getNoticeByKey(nid);
        modelMap.addAttribute("notice", notice);
        return prefix + "/review";
    }

    @PostMapping("/notice_review")
    @ResponseBody
    public Object noticeReview(NoticeReview noticeReview) {
        if (noticeReviewService.reviewNotice(noticeReview) > 0) {
            return AjaxResult.success("审核成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后再试！");
        }
    }

    //    @RequiresPermissions("teacher:del")
    @ResponseBody
    @PostMapping("/notice_review_del")
    public Object noticeReviewDel(int reviewId) {
        return noticeReviewService.deleteNoticeReview(reviewId);
    }

}

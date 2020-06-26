package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.base.BaseController;
import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName StuController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/14 11:47
 * @Version 1.0
 **/
@Api(value = "学生列表")
@Controller
@RequestMapping("/StuController")
public class StuController extends BaseController {
    private String prefix = "admin/stu";

//    @RequiresPermissions("stu:view")
    @GetMapping("/stu_view")
    public String userListView(ModelMap model) {
        return prefix + "/list";
    }

    @GetMapping("/stu_list")
    @ResponseBody
    public Object stuList(int page, int limit) {
        PageInfo<User> list = getList(page, limit, 3);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

//    @RequiresPermissions("stu:use")
    @ResponseBody
    @PostMapping("/useOrNot")
    public Object stuUseOrNot(String uid, int isUse) {
        return useOrNot(uid, isUse);
    }

//    @RequiresPermissions("stu:del")
    @ResponseBody
    @PostMapping("/stu_del")
    public Object stuDelete(String uid) {
        return del(uid);
    }

//    @RequiresPermissions("stu:detail")
    @GetMapping("/stu_detail")
    public String stuShow(String uid, ModelMap modelMap) {
        User user = userService.getUserByUid(uid);
        modelMap.addAttribute("user", user);
        return prefix + "/show";
    }

//    @RequiresPermissions("stu:search")
    @GetMapping("/search")
    @ResponseBody
    public Object search_stu(String condition, String content, int rid, int page, int limit) {
        PageInfo<User> list = userService.search(page, limit, condition, content, rid);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    @RequestMapping("/stu_edit_view")
    public String stu_edit_view(ModelMap modelMap, String uid) {
        edit_view(modelMap, uid);
        return prefix + "/edit";
    }

//    @RequiresPermissions("stu:edit")
    @PostMapping("/stu_edit")
    @ResponseBody
    public Object stu_edit(@RequestParam("image") Object image, User user, boolean isUpload) {
        return edit(image, user, isUpload);
    }
}

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
 * @ClassName TeacherController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/14 11:47
 * @Version 1.0
 **/
@Api(value = "教师列表")
@Controller
@RequestMapping("/TeacherController")
public class TeacherController extends BaseController {
    private String prefix = "admin/teacher";

    @GetMapping("/teacher_view")
    @RequiresPermissions("teacher:view")
    public String userListView(ModelMap model) {
        return prefix + "/list";
    }

    @GetMapping("/teacher_list")
    @ResponseBody
    public Object teacherList(int page, int limit) {
        PageInfo<User> list = getList(page, limit, 2);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //    @RequiresPermissions("teacher:use")
    @ResponseBody
    @PostMapping("/useOrNot")
    public Object teacherUseOrNot(String uid, int isUse) {
        return useOrNot(uid, isUse);
    }

    //    @RequiresPermissions("teacher:del")
    @ResponseBody
    @PostMapping("/teacher_del")
    public Object teacherDelete(String uid) {
        return del(uid);
    }

    //    @RequiresPermissions("teacher:detail")
    @GetMapping("/teacher_detail")
    public String teacherShow(String uid, ModelMap modelMap) {
        User user = userService.getUserByUid(uid);
        modelMap.addAttribute("user", user);
        return prefix + "/show";
    }

    //    @RequiresPermissions("teacher:search")
    @GetMapping("/search")
    @ResponseBody
    public Object search_teacher(String condition, String content, int rid, int page, int limit) {
        PageInfo<User> list = userService.search(page, limit, condition, content, rid);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    @RequestMapping("/teacher_edit_view")
    public String teacher_edit_view(ModelMap modelMap, String uid) {
        edit_view(modelMap, uid);
        return prefix + "/edit";
    }

    //    @RequiresPermissions("teacher:edit")
    @PostMapping("/teacher_edit")
    @ResponseBody
    public Object teacher_edit(@RequestParam("image") Object image, User user, boolean isUpload) {
        return edit(image, user, isUpload);
    }

}

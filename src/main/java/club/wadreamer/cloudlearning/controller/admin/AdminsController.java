package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.base.BaseController;
import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/13 21:16
 * @Version 1.0
 **/
@Api(value = "管理员列表")
@Controller
@RequestMapping("/AdminController")
public class AdminsController extends BaseController {

    private String prefix = "admin/admin";

//    @RequiresPermissions("admin:view")
    @GetMapping("/admin_view")
    public String userListView(ModelMap model) {
        return prefix + "/list";
    }

    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @GetMapping("/admin_list")
    @ResponseBody
    public Object adminList(int page, int limit) {
        PageInfo<User> list = getList(page, limit, 1);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

//    @RequiresPermissions("admin:use")
    @ResponseBody
    @PostMapping("/useOrNot")
    public Object AdminUseOrNot(String uid, int isUse) {
        return useOrNot(uid, isUse);
    }

//    @RequiresPermissions("admin:del")
    @ResponseBody
    @PostMapping("/admin_del")
    public Object AdminDelete(String uid) {
        return del(uid);
    }

//    @RequiresPermissions("admin:detail")
    @GetMapping("/admin_detail")
    public String adminShow(String uid, ModelMap modelMap) {
        User user = userService.getUserByUid(uid);
        modelMap.addAttribute("user", user);
        return prefix + "/show";
    }

//    @RequiresPermissions("admin:search")
    @GetMapping("/search")
    @ResponseBody
    public Object search_admin(String condition, String content, int rid, int page, int limit) {
        PageInfo<User> list = userService.search(page, limit, condition, content, rid);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    @RequestMapping("/admin_edit_view")
    public String admin_edit_view(ModelMap modelMap, String uid) {
        edit_view(modelMap, uid);
        return prefix + "/edit";
    }

//    @RequiresPermissions("admin:edit")
    @PostMapping("/admin_edit")
    @ResponseBody
    public Object admin_edit(@RequestParam("image") Object image, User user, boolean isUpload) {
        return edit(image, user, isUpload);
    }

}

package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.CourseSt;
import club.wadreamer.cloudlearning.service.CourseStService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CourseStController
 * @Description 课程一级分类控制器
 * @Author bear
 * @Date 2020/3/17 14:45
 * @Version 1.0
 **/
@Controller
@RequestMapping("/CourseStController")
public class CourseStController {
    private String prefix = "admin/course";

    @Autowired
    private CourseStService courseStService;

    /*
     * @Author bear
     * @Description 课程一级分类视图, 用于页面跳转
     * @Date 15:55 2020/3/17
     * @Param []
     * @return java.lang.String
     **/
    //@RequiresPermissions("course:cate_st")
    @GetMapping("/cate_st_view")
    public String cateStListView() {
        return prefix + "/cate_st";
    }

    /*
     * @Author bear
     * @Description 获取课程一级分类列表, 执行SQL操作
     * @Date 15:55 2020/3/17
     * @Param [page, limit]
     * @return java.lang.Object
     **/
    @GetMapping("/cate_st_list")
    @ResponseBody
    public Object cateStList(int page, int limit) {
        PageInfo<CourseSt> list = courseStService.getCateStList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    /*
     * @Author bear
     * @Description 删除课程一级分类,还未实现级联删除
     * @Date 15:56 2020/3/17
     * @Param [stId]
     * @return java.lang.Object
     **/
//    @RequiresPermissions("course:cate_st_del")
    @ResponseBody
    @PostMapping("/cate_st_del")
    public Object cateStDel(int stId) {
        return courseStService.deleteCourseSt(stId);
    }

    /*
     * @Author bear
     * @Description 新增课程一级分类视图, 用于页面跳转
     * @Date 15:56 2020/3/17
     * @Param []
     * @return java.lang.String
     **/
    //@RequiresPermissions("course:cate_st_add")
    @GetMapping("/cate_st_add_view")
    public String addCateStView() {
        return prefix + "/cate_st_add";
    }

    /*
     * @Author bear
     * @Description 新增课程一级分类, 执行SQL操作
     * @Date 15:56 2020/3/17
     * @Param [courseSt]
     * @return java.lang.Object
     **/
    @ResponseBody
    @PostMapping("/cate_st_add")
    public Object addCateSt(CourseSt courseSt) {
        if (courseStService.insertCateSt(courseSt) > 0) {
            return AjaxResult.success("新增成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    /*
     * @Author bear
     * @Description 一级分类修改视图
     * @Date 16:09 2020/3/17
     * @Param [modelMap, stId]
     * @return java.lang.String
     **/
    @RequestMapping("/cate_st_edit_view")
    public String cateStEditView(ModelMap modelMap, int stId) {
        modelMap.addAttribute("cateSt", courseStService.getCateStByStId(stId));
        return prefix + "/cate_st_edit";
    }

    /*
     * @Author bear
     * @Description 修改一级分类
     * @Date 16:23 2020/3/17
     * @Param [courseSt]
     * @return java.lang.Object
     **/
    //@RequiresPermissions("teacher:edit")
    @PostMapping("/cate_st_edit")
    @ResponseBody
    public Object cateStEdit(CourseSt courseSt) {
        if (courseStService.updateCateSt(courseSt) > 0) {
            return AjaxResult.success("修改成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }
}

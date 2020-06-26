package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.CourseNd;
import club.wadreamer.cloudlearning.service.CourseNdService;
import club.wadreamer.cloudlearning.service.CourseStService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName CourseNdController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/17 14:45
 * @Version 1.0
 **/
@Controller
@RequestMapping("/CourseNdController")
public class CourseNdController {

    private String prefix = "admin/course";

    @Autowired
    private CourseStService courseStService;

    @Autowired
    private CourseNdService courseNdService;

    //@RequiresPermissions("course:cate_st")
    @GetMapping("/cate_nd_view")
    public String cateNdListView() {
        return prefix + "/cate_nd";
    }

    @GetMapping("/cate_nd_list")
    @ResponseBody
    public Object cateNdList(int page, int limit) {
        PageInfo<CourseNd> list = courseNdService.getCateNdList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //@RequiresPermissions("course:cate_nd_add")
    @GetMapping("/cate_nd_add_view")
    public String addCateNdView(ModelMap modelMap) {
        modelMap.addAttribute("cateSts", courseStService.getAllCateSt());
        return prefix + "/cate_nd_add";
    }

    @ResponseBody
    @PostMapping("/cate_nd_add")
    public Object addCateNd(CourseNd courseNd) {
        if (courseNdService.insertCateNd(courseNd) > 0) {
            return AjaxResult.success("新增成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    //@RequiresPermissions("course:cate_nd_del")
    @ResponseBody
    @PostMapping("/cate_nd_del")
    public Object cateNdDel(int ndId) {
        return courseNdService.deleteCateNd(ndId);
    }

    @RequestMapping("/cate_nd_edit_view")
    public String cateNdEditView(ModelMap modelMap, int ndId) {
        modelMap.addAttribute("cateSts", courseStService.getAllCateSt());
        modelMap.addAttribute("cateNd", courseNdService.getCateNd(ndId));
        return prefix + "/cate_nd_edit";
    }

    @PostMapping("/cate_nd_edit")
    @ResponseBody
    public Object cateNdEdit(CourseNd courseNd) {
        if (courseNdService.updateCateNd(courseNd) > 0) {
            return AjaxResult.success("修改成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    @PostMapping("/getCate_nd")
    @ResponseBody
    public Object getCateNd(int stId) {
        List<CourseNd> list = courseNdService.getCateNdByStId(stId);
        return AjaxResult.success(list);
    }
}

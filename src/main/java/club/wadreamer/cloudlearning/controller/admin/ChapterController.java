package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Chapter;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.Video;
import club.wadreamer.cloudlearning.service.ChapterService;
import club.wadreamer.cloudlearning.service.CourseService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
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
 * @ClassName ChapterController
 * @Description TODO
 * @Author bear
 * @Date 2020/4/9 16:10
 * @Version 1.0
 **/
@Api(value = "章节列表")
@Controller
@RequestMapping("/ChapterController")
public class ChapterController {
    private String prefix = "admin/chapter";

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CourseService courseService;

    //    @RequiresPermissions("chapter:view")
    @GetMapping("/chapter_view")
    public String chapterListView() {
        return prefix + "/list";
    }

    @GetMapping("/chapter_list")
    @ResponseBody
    public Object chapterList(int page, int limit) {
        PageInfo<Chapter> list = chapterService.getChapterList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    //@RequiresPermissions("course:add_view")
    @GetMapping("/chapter_add_view")
    public String chapterAddView(ModelMap modelMap) {
        List<Course> coursePassList = courseService.getCoursePassList();
        modelMap.addAttribute("coursePassList", coursePassList);
        return prefix + "/add";
    }

    @PostMapping("/chapter_add")
    @ResponseBody
    public Object chapterAdd(Chapter chapter) {
        chapter.setUid(ShiroUtils.getUserId());
        if (chapterService.insertChapter(chapter) > 0) {
            return AjaxResult.success("新增成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    @GetMapping("/chapter_edit_view")
    public String chapterEditView(ModelMap modelMap, int chapterId) {
        Chapter chapter = chapterService.getChapterByKey(chapterId);
        List<Course> coursePassList = courseService.getCoursePassList();
        modelMap.addAttribute("chapter", chapter);
        modelMap.addAttribute("coursePassList", coursePassList);
        return prefix + "/edit";
    }

    @PostMapping("/chapter_edit")
    @ResponseBody
    public Object chapterEdit(Chapter chapter) {
        chapter.setUid(ShiroUtils.getUserId());
        if (chapterService.updateChapter(chapter) > 0) {
            return AjaxResult.success("修改成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    @PostMapping("/chapters")
    @ResponseBody
    public Object getChaptersByCid(int cid) {
        List<Chapter> list = chapterService.getChaptersByCid(cid);
        return AjaxResult.success(list);
    }

    @PostMapping("/chapter_del")
    @ResponseBody
    public Object delChapter(int chapterId) {
        return chapterService.delChapter(chapterId);
    }


}

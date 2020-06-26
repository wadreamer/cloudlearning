package club.wadreamer.cloudlearning.controller.admin;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Chapter;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.Video;
import club.wadreamer.cloudlearning.service.CourseService;
import club.wadreamer.cloudlearning.service.VideoService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.JsonUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName VideoController
 * @Description TODO
 * @Author bear
 * @Date 2020/4/10 12:32
 * @Version 1.0
 **/
@Api(value = "视频上传")
@Controller
@RequestMapping("/VideoController")
public class VideoController {

    private String prefix = "admin/video";

    @Autowired
    private CourseService courseService;

    @Autowired
    private VideoService videoService;

    //    @RequiresPermissions("course:view")
    @GetMapping("/video_upload_view")
    public String videoUploadView(ModelMap modelMap) {
        List<Course> courses = courseService.getCoursePassList();
        modelMap.addAttribute("courses", courses);
        return prefix + "/upload";
    }

    @PostMapping("/video_upload")
    @ResponseBody
    public Object videoUpload(MultipartFile file, Video video) {
        System.out.println(JsonUtils.gsonString(file));
        System.out.println(JsonUtils.gsonString(video));

        System.out.println(JsonUtils.gsonString(file));

        return videoService.uploadVideo(file, video);

    }

    //    @RequiresPermissions("course:view")
    @GetMapping("/video_view")
    public String videoView() {
        return prefix + "/list";
    }

    @GetMapping("/video_list")
    @ResponseBody
    public Object videoList(int page, int limit) {
        PageInfo<club.wadreamer.cloudlearning.model.custom.Video> list =
                videoService.getVideoList(page, limit);
        return AjaxResult.success_listData("", list.getList(), list.getTotal());
    }

    @PostMapping("/video_del")
    @ResponseBody
    public Object deleteVideo(int vid) {
        return videoService.deleteVideoByKey(vid);
    }

}

package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.common.file.FileUtils;
import club.wadreamer.cloudlearning.mapper.auto.ChapterMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseNdMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseStMapper;
import club.wadreamer.cloudlearning.model.auto.*;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName CourseService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/15 11:43
 * @Version 1.0
 **/
@Service
public class CourseService {

    @Value("${course_img.upload.path}")
    private String courseImgPath;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    /*
     * @Author bear
     * @Description 获取教师自己创建的课程信息
     * @Date 20:07 2020/3/17
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<club.wadreamer.cloudlearning.model.auto.Course>
     **/
    public PageInfo<Course> getCourseListByUserId(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String uid = ShiroUtils.getUserId();
        List<Course> list = courseMapper.getCourseListByUserId(uid);
        PageInfo<Course> pageInfo = new PageInfo<Course>(list);
        return pageInfo;
    }

    /*
     * @Author bear
     * @Description //TODO 获取所有已开设且未订阅的课程, 用于选课
     * @Date 12:36 2020/4/11
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<club.wadreamer.cloudlearning.model.auto.Course>
     **/
    public PageInfo<Course> getAllCourseForSelect(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseMapper.getAllCourseListForSelect(ShiroUtils.getUserId());
        PageInfo<Course> pageInfo = new PageInfo<Course>(list);
        return pageInfo;
    }


    /*
     * @Author bear
     * @Description 根据用户主键, 获取已经通过审核的课程信息
     * @Date 16:47 2020/4/9
     * @Param []
     * @return java.util.List<club.wadreamer.cloudlearning.model.auto.Course>
     **/
    public List<Course> getCoursePassList() {
        String uid = ShiroUtils.getUserId();
        return courseMapper.getCoursePassList(uid);
    }

    @Transactional
    public Object insertCourse(MultipartFile image, Course course) {
        User user = ShiroUtils.getUser();
        course.setUid(user.getUid());
        course.setUsername(user.getUsername());

        String fileName = image.getOriginalFilename(); // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀名
        fileName = UUID.randomUUID() + "_" + ShiroUtils.getUser().getUsername() + suffixName; // 上传后的文件名

        String dirPath = "/static/course_img/" + user.getUsername();
        File dir = new File(courseImgPath + dirPath); // 将文件写入磁盘
        if (!dir.exists()) {//如果文件夹不存在
            dir.mkdir();//创建文件夹
        }

        File dest = new File(courseImgPath + dirPath + "/" + fileName); // 将文件写入磁盘

        try {
            course.setImgPath(dirPath + "/" + fileName);

            image.transferTo(dest);
            int result = courseMapper.insertSelective(course);
            if (result > 0) {
                return AjaxResult.success();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return AjaxResult.error();
    }

    @Transactional
    public Object updateCourse(MultipartFile image, Course course, boolean isUpload) {
        String username = ShiroUtils.getUserName();
        Course old = courseMapper.selectByPrimaryKey(course.getCid());
        if (isUpload) {
            String fileName = image.getOriginalFilename();//文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//后缀名
            fileName = UUID.randomUUID() + "_" + username + suffixName;//上传后的文件名+后缀名

            String dirPath = "/static/course_img/" + username;
            File dir = new File(courseImgPath + dirPath); // 将文件写入磁盘
            if (!dir.exists()) {//如果文件夹不存在
                dir.mkdir();//创建文件夹
            }

            course.setImgPath(dirPath + "/" + fileName);

            File dest = new File(courseImgPath + dirPath + "/" + fileName); // 将文件写入磁盘

            try {
                image.transferTo(dest);//头像写入磁盘

                int result = courseMapper.updateByPrimaryKeySelective(course);

                if (result > 0) {
                    boolean result_delImg = FileUtils.deleteFile(courseImgPath + old.getImgPath());// 删除原来的文件
                    if(result_delImg) {
                        return AjaxResult.success("修改成功!");
                    }else{
                        System.out.println("444444444");
                        return AjaxResult.error(-1, "操作失败，请稍后重试");
                    }
                } else {
                    System.out.println("3333333333");
                    return AjaxResult.error(-1, "操作失败，请稍后重试");
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            int result = courseMapper.updateByPrimaryKeySelective(course);
            if (result > 0) {
                return AjaxResult.success("修改成功!");
            } else {
                System.out.println("2222222");
                return AjaxResult.error(-1, "操作失败，请稍后重试");
            }
        }
        System.out.println("11111111");
        return AjaxResult.error(-1, "操作失败，请稍后重试");
    }

    public Course getCourseByKey(int cid) {
        return courseMapper.selectByPrimaryKey(cid);
    }

    @Transactional
    public Object delCourse(int cid) {
        List<Chapter> list = chapterMapper.getChaptersByCid(cid, ShiroUtils.getUserId());
        if (list.size() > 0) {
            return AjaxResult.error("无法删除,该课程下还有其他章节~~");
        } else {
            int result = courseMapper.deleteByPrimaryKey(cid);
            if (result > 0) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请稍后重试~~");
            }
        }
    }
}

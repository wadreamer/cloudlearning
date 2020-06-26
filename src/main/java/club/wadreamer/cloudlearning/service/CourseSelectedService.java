package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.CourseMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseSelectedMapper;
import club.wadreamer.cloudlearning.mapper.custom.CourseSelectedDao;
import club.wadreamer.cloudlearning.model.auto.CourseSelected;
import club.wadreamer.cloudlearning.model.custom.ChapterAndVideo;
import club.wadreamer.cloudlearning.model.custom.CourseWithChapterAndVideo;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CourseSelectedService
 * @Description TODO
 * @Author bear
 * @Date 2020/4/11 12:51
 * @Version 1.0
 **/
@Service
public class CourseSelectedService {

    @Autowired
    private CourseSelectedMapper courseSelectedMapper;

    @Autowired
    private CourseSelectedDao courseSelectedDao;

    @Autowired
    private CourseMapper course;

    @Transactional
    public Object insertCourseSelected(CourseSelected courseSelected) {
        courseSelected.setUid(ShiroUtils.getUserId());
        if (courseSelectedMapper.insertSelective(courseSelected) > 0) {
            return AjaxResult.success("订阅成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试");
        }
    }

    public PageInfo<club.wadreamer.cloudlearning.model.custom.CourseSelected> getCourseSelectedList(int pageNum, int pageSize, String uid) {
        PageHelper.startPage(pageNum, pageSize);
//        List<CourseSelected> list = courseSelectedMapper.getCourseSelectedList(uid);
        List<club.wadreamer.cloudlearning.model.custom.CourseSelected> list =
                courseSelectedDao.getCourseSelectByUid(uid);
        PageInfo<club.wadreamer.cloudlearning.model.custom.CourseSelected> pageInfo =
                new PageInfo<club.wadreamer.cloudlearning.model.custom.CourseSelected>(list);
        return pageInfo;
    }

    @Transactional
    public Object cancelSub(int sid) {
        if (courseSelectedMapper.deleteByPrimaryKey(sid) > 0) {
            return AjaxResult.success("取消成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试");
        }
    }

    public CourseWithChapterAndVideo getCourseAllChapterWithVideo(int cid) {
        CourseWithChapterAndVideo courseWithChapterAndVideo = courseSelectedDao.getCourseInfoByCid(cid);
        courseWithChapterAndVideo.setList(courseSelectedDao.getChapterAndVideosList(cid));

        return courseWithChapterAndVideo;
    }


}

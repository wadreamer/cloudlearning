package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.CourseMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseNdMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseStMapper;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.CourseSt;
import club.wadreamer.cloudlearning.util.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CourseStService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/17 14:47
 * @Version 1.0
 **/
@Service
public class CourseStService {
    @Autowired
    private CourseStMapper courseStMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseNdMapper courseNdMapper;

    /*
     * @Author bear
     * @Description 获取课程一级分类的列表,用于生成分页的list
     * @Date 15:02 2020/3/17
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<club.wadreamer.cloudlearning.model.auto.CourseSt>
     **/
    public PageInfo<CourseSt> getCateStList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseSt> list = courseStMapper.getList();
        PageInfo<CourseSt> pageInfo = new PageInfo<CourseSt>(list);
        return pageInfo;
    }

    /*
     * @Author bear
     * @Description 获取课程所有的一级分类,用户select下拉选项
     * @Date 15:34 2020/3/17
     * @Param []
     * @return java.util.List<club.wadreamer.cloudlearning.model.auto.CourseSt>
     **/
    public List<CourseSt> getAllCateSt() {
        return courseStMapper.getList();
    }

    /*
     * @Author bear
     * @Description 添加课程一级分类
     * @Date 15:35 2020/3/17
     * @Param [courseSt]
     * @return int
     **/
    @Transactional
    public int insertCateSt(CourseSt courseSt) {
        System.out.println(JsonUtils.gsonString(courseSt));
        return courseStMapper.insert(courseSt);
    }

    /*
     * @Author bear
     * @Description 根据一级分类主键修改一级分类
     * @Date 16:05 2020/3/17
     * @Param [courseSt]
     * @return int
     **/
    @Transactional
    public int updateCateSt(CourseSt courseSt) {
        return courseStMapper.updateByPrimaryKeySelective(courseSt);
    }

    public CourseSt getCateStByStId(int stId) {
        return courseStMapper.selectByPrimaryKey(stId);
    }

    /*
     * @Author bear
     * @Description 删除课程一级分类,同时级联删除下有的课程,章节,和视频
     * @Date 15:35 2020/3/17
     * @Param [st_id]
     * @return int
     **/
    @Transactional
    public Object deleteCourseSt(int stId) {
        List<Course> list = courseMapper.checkIsMoreCourseBystId(stId);
        if (list.size() > 0) {
            return AjaxResult.error("无法删除,该一级分类下还有开设的课程~~");
        } else {
            int result_st = courseStMapper.deleteByPrimaryKey(stId);
            int result_nd = courseNdMapper.deleteBystId(stId);
            if (result_st > 0 && result_nd > 0) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请稍后重试~~");
            }
        }
    }

}

package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.CourseMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseNdMapper;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.CourseNd;
import club.wadreamer.cloudlearning.model.auto.CourseSt;
import club.wadreamer.cloudlearning.util.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CourseNdService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/17 14:47
 * @Version 1.0
 **/
@Service
public class CourseNdService {
    @Autowired
    private CourseNdMapper courseNdMapper;

    @Autowired
    private CourseMapper courseMapper;

    public PageInfo<CourseNd> getCateNdList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseNd> list = courseNdMapper.getList();
        PageInfo<CourseNd> pageInfo = new PageInfo<CourseNd>(list);
        return pageInfo;
    }

    @Transactional
    public int insertCateNd(CourseNd courseNd) {
        System.out.println(JsonUtils.gsonString(courseNd));
        return courseNdMapper.insert(courseNd);
    }

    @Transactional
    public Object deleteCateNd(int ndId) {
        List<Course> list = courseMapper.checkIsMoreCourseByndId(ndId);
        if(list.size() > 0){
            return AjaxResult.error("无法删除,该二级分类下还有开设的课程~~");
        }else{
            if(courseNdMapper.deleteByPrimaryKey(ndId) > 0){
                return AjaxResult.success("删除成功");
            }else{
                return AjaxResult.error("删除失败，请稍后重试~~");
            }
        }
    }

    public CourseNd getCateNd(int ndId) {
        return courseNdMapper.selectByPrimaryKey(ndId);
    }

    @Transactional
    public int updateCateNd(CourseNd courseNd) {
        return courseNdMapper.updateByPrimaryKeySelective(courseNd);
    }

    public List<CourseNd> getCateNdByStId(int stId) {
        return courseNdMapper.getCourseNdByStId(stId);
    }

}

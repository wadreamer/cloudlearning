package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.CourseMapper;
import club.wadreamer.cloudlearning.mapper.auto.ReviewLogMapper;
import club.wadreamer.cloudlearning.mapper.custom.CourseReviewDao;
import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.ReviewLog;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.model.custom.CourseReviewLog;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ReviewLogService
 * @Description TODO
 * @Author bear
 * @Date 2020/4/9 20:28
 * @Version 1.0
 **/
@Service
public class ReviewLogService {

    @Autowired
    private ReviewLogMapper reviewLogMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseReviewDao courseReviewDao;

    /*
     * @Author bear
     * @Description //TODO 获取待审核的课程列表
     * @Date 17:50 2020/4/9
     * @Param []
     * @return java.util.List<club.wadreamer.cloudlearning.model.auto.Course>
     **/
    public PageInfo<Course> getUnreviewList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseReviewDao.getUnreviewList();
        PageInfo<Course> pageInfo = new PageInfo<Course>(list);
        return pageInfo;
    }

    /*
     * @Author bear
     * @Description //TODO 获取审核未通过的列表
     * @Date 22:14 2020/4/9
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<club.wadreamer.cloudlearning.model.custom.CourseReviewLog>
     **/
    public PageInfo<CourseReviewLog> getUnpassList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<CourseReviewLog> list = courseReviewDao.getUnpassList();
        PageInfo<CourseReviewLog> pageInfo = new PageInfo<CourseReviewLog>(list);
        return pageInfo;
    }

    /*
     * @Author bear
     * @Description //TODO 获取审核通过的列表
     * @Date 22:34 2020/4/9
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<club.wadreamer.cloudlearning.model.custom.CourseReviewLog>
     **/
    public PageInfo<CourseReviewLog> getPassList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<CourseReviewLog> list = courseReviewDao.getPassList();
        PageInfo<CourseReviewLog> pageInfo = new PageInfo<CourseReviewLog>(list);
        return pageInfo;
    }

    /*
     * @Author bear
     * @Description //TODO 实现审核功能, 提交管理员审核记录
     * @Date 22:12 2020/4/9
     * @Param [reviewLog]
     * @return int
     **/
    @Transactional
    public int insertSelective(ReviewLog reviewLog) {
        reviewLog.setReviewDate(DateUtils.nowTime());

        User user = ShiroUtils.getUser();
        reviewLog.setUid(user.getUid());
        reviewLog.setUsername(user.getUsername());

        int result_review = reviewLogMapper.insertSelective(reviewLog);
        int result_update = courseMapper.updatePassByKey(reviewLog);
        return (result_review > 0 && result_update > 0) ? 1 : 0;
    }

    @Transactional
    public Object deleteByKey(int reviewId){
        if (reviewLogMapper.deleteByPrimaryKey(reviewId) > 0) {
            return AjaxResult.success("删除成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

}

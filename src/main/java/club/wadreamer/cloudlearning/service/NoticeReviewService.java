package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.NoticeMapper;
import club.wadreamer.cloudlearning.mapper.auto.NoticeReviewMapper;
import club.wadreamer.cloudlearning.mapper.custom.CourseSelectedDao;
import club.wadreamer.cloudlearning.mapper.custom.NoticeCourseDao;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.model.auto.NoticeReview;
import club.wadreamer.cloudlearning.model.custom.CourseSelected;
import club.wadreamer.cloudlearning.model.custom.NoticeCourse;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @ClassName NoticeReviewService
 * @Description TODO
 * @Author bear
 * @Date 2020/4/10 22:07
 * @Version 1.0
 **/
@Service
public class NoticeReviewService {
    @Autowired
    private NoticeReviewMapper noticeReviewMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private CourseSelectedDao courseSelectedDao;

    @Autowired
    private NoticeCourseDao noticeCourseDao;

    /*
     * @Author bear
     * @Description //TODO 根据status参数, 分别获取审核未通过和审核通过的公告
     * @Date 10:38 2020/4/11
     * @Param [pageNum, pageSize, status]
     * @return com.github.pagehelper.PageInfo
     **/
    public PageInfo getHasReviewList(int pageNum, int pageSize, int status) {
        PageHelper.startPage(pageNum, pageSize);
        List<NoticeReview> list = noticeReviewMapper.getHasReviewList(status);
        PageInfo<NoticeReview> pageInfo = new PageInfo<NoticeReview>(list);
        return pageInfo;
    }

    @Transactional
    public int reviewNotice(NoticeReview noticeReview) {
        noticeReview.setUid(ShiroUtils.getUserId());
        noticeReview.setReviewDate(DateUtils.nowTime());
        int result_review = noticeReviewMapper.insertSelective(noticeReview);
        int result_update_notice = noticeMapper.updateStatusAndAdviceByNid(noticeReview.getNid(), noticeReview.getStatus(), noticeReview.getAdvice());

        // 1. 若公告审核通过，则将该公告发布到订阅该公告对应的课程的学生中
        // 2. 根据审核通过的公告的nid获得该条原始公告对应的课程主键cid
        // 3. 根据cid
        if (noticeReview.getStatus() == 2) {
            Notice notice = noticeMapper.selectByPrimaryKey(noticeReview.getNid()); // 根据nid获取该公告
            // 根据cid所有订阅了该公告对应的课程的学生
            List<CourseSelected> csList = courseSelectedDao.getAllCourseSelectedByCid(notice.getCid());
            for (CourseSelected cs : csList) {
                NoticeCourse nc = new NoticeCourse(cs.getUid(), notice.getNid());
                noticeCourseDao.insertNoticeCourse(nc);
            }
        }

        return (result_review > 0 && result_update_notice > 0) ? 1 : 0;
    }

    @Transactional
    public Object deleteNoticeReview(int reviewId) {
        if (noticeReviewMapper.deleteByPrimaryKey(reviewId) > 0) {
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("操作失败，请稍后重试");
        }
    }

}

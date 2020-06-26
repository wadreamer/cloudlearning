package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.custom.NoticeCourseDao;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.model.custom.NoticeCourse;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName NoticeCourseService
 * @Description TODO
 * @Author bear
 * @Date 2020/5/2 17:43
 * @Version 1.0
 **/
@Service
public class NoticeCourseService {

    @Autowired
    private NoticeCourseDao noticeCourseDao;

    public PageInfo<NoticeCourse> getNoticeCourseAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        User user = ShiroUtils.getUser();
        List<NoticeCourse> list = noticeCourseDao.getAll(user.getUid());
        PageInfo<NoticeCourse> pageInfo = new PageInfo<NoticeCourse>(list);
        return pageInfo;
    }

    @Transactional
    public void updateReadStatus(int ncid) {
        noticeCourseDao.updateReadStatus(ncid);
    }

    @Transactional
    public Object courseNoticeDel(int ncid) {
        if (noticeCourseDao.courseNoticeDel(ncid) > 0) {
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }

    }


}

package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.NoticeMapper;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName NoticeService
 * @Description TODO
 * @Author bear
 * @Date 2020/4/10 20:19
 * @Version 1.0
 **/
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public PageInfo<Notice> getNoticeList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.getNoticeListByUid(ShiroUtils.getUserId());
        PageInfo<Notice> pageInfo = new PageInfo<Notice>(list);
        return pageInfo;
    }

    public Notice getNoticeByKey(int nid) {
        return noticeMapper.getNoticeByKey(nid);
    }

    public PageInfo<Notice> getUnreviewList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.getUnreviewList();
        PageInfo<Notice> pageInfo = new PageInfo<Notice>(list);
        return pageInfo;
    }

    @Transactional
    public int insertNoticeSelective(Notice notice) {
        User user = ShiroUtils.getUser();
        notice.setUid(user.getUid());
        notice.setUsername(user.getUsername());
        notice.setPublishDate(DateUtils.nowTime());

        return noticeMapper.insertSelective(notice);
    }



    @Transactional
    public Object updateNotice(Notice notice) {
        int result = noticeMapper.updateByPrimaryKeySelective(notice);
        if(result > 0){
            return AjaxResult.success("修改成功");
        }else{
            return AjaxResult.error("修改失败，请稍后重试");
        }
    }

    @Transactional
    public Object deleteNotice(int nid){
        if(noticeMapper.deleteByPrimaryKey(nid) > 0){
            return AjaxResult.success("删除成功");
        }else {
            return AjaxResult.error("操作失败，请稍后重试");
        }
    }

}

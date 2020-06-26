package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.ChapterMapper;
import club.wadreamer.cloudlearning.mapper.auto.CourseMapper;
import club.wadreamer.cloudlearning.mapper.auto.VideoMapper;
import club.wadreamer.cloudlearning.model.auto.Chapter;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.model.auto.Video;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.FileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName ChapterService
 * @Description TODO
 * @Author bear
 * @Date 2020/4/9 16:25
 * @Version 1.0
 **/
@Service
public class ChapterService {

    @Value("${video.upload.path}")
    private String videoUploadPath;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VideoMapper videoMapper;

    public PageInfo<Chapter> getChapterList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        User user = ShiroUtils.getUser();
        List<Chapter> list = chapterMapper.getChapterList(user.getUid());
        PageInfo<Chapter> pageInfo = new PageInfo<Chapter>(list);
        return pageInfo;
    }

    @Transactional
    public int insertChapter(Chapter chapter) {
        String cname = courseMapper.selectByPrimaryKey(chapter.getCid()).getCname();//获取课程名
        chapter.setCname(cname);

        int result = chapterMapper.insertSelective(chapter);
        return result;
    }

    @Transactional
    public int updateChapter(Chapter chapter) {
        String cname = courseMapper.selectByPrimaryKey(chapter.getCid()).getCname();//获取课程名
        chapter.setCname(cname);
        return chapterMapper.updateByPrimaryKeySelective(chapter);
    }

    public Chapter getChapterByKey(int chapterId) {
        return chapterMapper.selectByPrimaryKey(chapterId);
    }

    public List<Chapter> getChaptersByCid(int cid) {
        return chapterMapper.getChaptersByCid(cid, ShiroUtils.getUserId());
    }

    @Transactional
    public Object delChapter(int chapterId) {
        List<Video> list = videoMapper.checkIsMoreVideos(chapterId);
        if (list.size() > 0) {
            boolean flag = false;
            for (Video video : list) {
                String filePath = videoUploadPath + video.getVideoPath();
                flag = FileUtils.delFile(filePath);
            }
            int result = chapterMapper.deleteByPrimaryKey(chapterId);
            if (flag && result > 0) {
                return AjaxResult.success("删除成功！");
            } else {
                return AjaxResult.error("操作失败，请稍后重试");
            }
        } else {
            if (chapterMapper.deleteByPrimaryKey(chapterId) > 0) {
                return AjaxResult.success("删除成功！");
            } else {
                return AjaxResult.error("操作失败，请稍后重试");
            }
        }
    }
}

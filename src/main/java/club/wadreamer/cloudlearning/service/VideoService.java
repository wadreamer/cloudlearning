package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.mapper.auto.VideoMapper;
import club.wadreamer.cloudlearning.mapper.custom.VideoDao;
import club.wadreamer.cloudlearning.model.auto.Chapter;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.model.auto.Video;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.DateUtils;
import club.wadreamer.cloudlearning.util.FileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName VideoService
 * @Description TODO
 * @Author bear
 * @Date 2020/4/10 12:39
 * @Version 1.0
 **/
@Service
public class VideoService {

    @Value("${video.upload.path}")
    private String videoUploadPath;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoDao videoDao;

    @Transactional
    public Object uploadVideo(MultipartFile file, Video video) {
        User user = ShiroUtils.getUser();
        String fileName = file.getOriginalFilename(); // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀名
        fileName = UUID.randomUUID() + "_" + ShiroUtils.getUser().getUsername() + suffixName; // 上传后的文件名

        String dirPath = "/static/videos/" + user.getUsername();
        File dir = new File(videoUploadPath + dirPath); // 将文件写入磁盘
        if (!dir.exists()) {//如果文件夹不存在
            dir.mkdir();//创建文件夹
        }

        File dest = new File(videoUploadPath + dirPath + "/" + fileName); // 将文件写入磁盘
        try {
            video.setUid(user.getUid());
            video.setVideoPath(dirPath + "/" + fileName);
            video.setUploadDate(DateUtils.nowTime());

            int result = videoMapper.insertSelective(video);
            file.transferTo(dest);

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

    public PageInfo<club.wadreamer.cloudlearning.model.custom.Video> getVideoList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        User user = ShiroUtils.getUser();
        List<club.wadreamer.cloudlearning.model.custom.Video> list = videoDao.getVideoListByUid(user.getUid());
        PageInfo<club.wadreamer.cloudlearning.model.custom.Video> pageInfo =
                new PageInfo<club.wadreamer.cloudlearning.model.custom.Video>(list);
        return pageInfo;
    }

    @Transactional
    public Object deleteVideoByKey(Integer vid) {
        Video video = videoMapper.selectByPrimaryKey(vid);
        if (video != null) {
            String filePath = videoUploadPath + video.getVideoPath();
            boolean result_del = FileUtils.delFile(filePath);
            if(result_del){
                if (videoMapper.deleteByPrimaryKey(vid) > 0) {
                    return AjaxResult.success("删除成功");
                } else {
                    return AjaxResult.error("操作失败，请稍后重试");
                }
            }
        }
        return AjaxResult.error("操作失败，请稍后重试");
    }

}

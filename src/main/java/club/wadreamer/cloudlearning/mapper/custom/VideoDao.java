package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.custom.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    List<Video> getVideoListByUid(@Param("uid") String uid);
}

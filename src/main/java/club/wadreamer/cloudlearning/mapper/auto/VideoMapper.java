package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.Video;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer vid);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer vid);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> checkIsMoreVideos(int chapterId);

}
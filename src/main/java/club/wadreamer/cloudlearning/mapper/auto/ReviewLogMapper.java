package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.ReviewLog;

public interface ReviewLogMapper {
    int deleteByPrimaryKey(Integer reviewId);

    int insert(ReviewLog record);

    int insertSelective(ReviewLog record);

    ReviewLog selectByPrimaryKey(Integer reviewId);

    int updateByPrimaryKeySelective(ReviewLog record);

    int updateByPrimaryKey(ReviewLog record);
}
package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.NoticeReview;

import java.util.List;

public interface NoticeReviewMapper {
    int deleteByPrimaryKey(Integer reviewId);

    int insert(NoticeReview record);

    int insertSelective(NoticeReview record);

    NoticeReview selectByPrimaryKey(Integer reviewId);

    int updateByPrimaryKeySelective(NoticeReview record);

    int updateByPrimaryKey(NoticeReview record);

    List<NoticeReview> getHasReviewList(Integer status);

}
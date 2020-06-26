package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer nid);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer nid);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> getNoticeListByUid(String uid);

    Notice getNoticeByKey(Integer nid);

    List<Notice> getUnreviewList();

    int updateStatusAndAdviceByNid(@Param("nid") Integer nid, @Param("status") Integer status,
                                   @Param("advice") String advice);
}
package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.custom.NoticeCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeCourseDao {
    List<NoticeCourse> getAll(@Param("uid") String uid);

    int insertNoticeCourse(NoticeCourse noticeCourse);

    int updateReadStatus(@Param("ncid") Integer ncid);

    int courseNoticeDel(@Param("ncid") Integer ncid);

}

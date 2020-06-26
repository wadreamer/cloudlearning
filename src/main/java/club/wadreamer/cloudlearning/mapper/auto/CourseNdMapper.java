package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.CourseNd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseNdMapper {
    int deleteByPrimaryKey(Integer ndId);

    int insert(CourseNd record);

    int insertSelective(CourseNd record);

    CourseNd selectByPrimaryKey(Integer ndId);

    int updateByPrimaryKeySelective(CourseNd record);

    int updateByPrimaryKey(CourseNd record);

    //--------------------------------------
    List<CourseNd> getList();

    List<CourseNd> getCourseNdByStId(@Param("stId") int stId);

    int deleteBystId(@Param("stId") Integer stId);
}
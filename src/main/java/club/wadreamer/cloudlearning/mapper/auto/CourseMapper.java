package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.auto.ReviewLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer cid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> getCourseListByUserId(String uid);

    List<Course> getCoursePassList(String uid);

    int updatePassByKey(ReviewLog reviewLog);

    List<Course> getAllCourseListForSelect(String uid);

    List<Course> checkIsMoreCourseBystId(@Param("stId") Integer stId);

    List<Course> checkIsMoreCourseByndId(@Param("ndId") Integer ndId);

}
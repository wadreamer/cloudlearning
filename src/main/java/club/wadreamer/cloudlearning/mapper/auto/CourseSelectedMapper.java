package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.CourseSelected;

import java.util.List;

public interface CourseSelectedMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(CourseSelected record);

    int insertSelective(CourseSelected record);

    CourseSelected selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(CourseSelected record);

    int updateByPrimaryKey(CourseSelected record);

    List<CourseSelected> getCourseSelectedList(String uid);

}
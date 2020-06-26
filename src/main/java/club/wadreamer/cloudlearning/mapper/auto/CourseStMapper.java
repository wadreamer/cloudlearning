package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.CourseSt;

import java.util.List;

public interface CourseStMapper {
    int deleteByPrimaryKey(Integer stId);

    int insert(CourseSt record);

    int insertSelective(CourseSt record);

    CourseSt selectByPrimaryKey(Integer stId);

    int updateByPrimaryKeySelective(CourseSt record);

    int updateByPrimaryKey(CourseSt record);

    List<CourseSt> getList();
}
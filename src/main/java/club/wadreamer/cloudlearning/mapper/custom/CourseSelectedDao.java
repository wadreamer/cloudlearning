package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.custom.ChapterAndVideo;
import club.wadreamer.cloudlearning.model.custom.CourseSelected;
import club.wadreamer.cloudlearning.model.custom.CourseWithChapterAndVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseSelectedDao {
    List<CourseSelected> getCourseSelectByUid(String uid);

    List<CourseSelected> getAllCourseSelectedByCid(Integer cid);

    List<ChapterAndVideo> getChapterAndVideosList(Integer cid);

    CourseWithChapterAndVideo getCourseInfoByCid(@Param("cid") Integer cid);

}

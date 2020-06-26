package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.auto.Course;
import club.wadreamer.cloudlearning.model.custom.CourseReviewLog;

import java.util.List;

public interface CourseReviewDao {
    List<Course> getUnreviewList();

    List<CourseReviewLog> getUnpassList();

    List<CourseReviewLog> getPassList();
}

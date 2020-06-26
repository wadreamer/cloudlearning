package club.wadreamer.cloudlearning.model.custom;

/**
 * @ClassName CourseSelected
 * @Description TODO
 * @Author bear
 * @Date 2020/5/1 12:25
 * @Version 1.0
 **/
public class CourseSelected {
    private Integer sid;

    private String uid; // 选课学生

    private Integer cid; // 课程主键

    private String cname; // 课程名称

    private String teacher; // 授课教师

    private String courseDescription; // 课程描述

    private String imgPath; // 课程图片路径

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

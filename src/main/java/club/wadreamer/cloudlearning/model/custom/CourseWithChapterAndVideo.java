package club.wadreamer.cloudlearning.model.custom;

import java.util.List;

/**
 * @ClassName CourseWithChapterAndVideo
 * @Description TODO
 * @Author bear
 * @Date 2020/5/3 11:43
 * @Version 1.0
 **/
public class CourseWithChapterAndVideo {
    private String cname;

    private String imgPath;

    private String teacher;

    private List<ChapterAndVideo> list;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<ChapterAndVideo> getList() {
        return list;
    }

    public void setList(List<ChapterAndVideo> list) {
        this.list = list;
    }
}

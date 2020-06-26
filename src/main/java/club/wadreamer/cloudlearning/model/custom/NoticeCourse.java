package club.wadreamer.cloudlearning.model.custom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @ClassName NoticeCourse
 * @Description TODO
 * @Author bear
 * @Date 2020/5/2 17:31
 * @Version 1.0
 **/
public class NoticeCourse {
    private Integer ncid; // 学生的课程公告主键

    private String publisher; // 发布者

    private String cname; // 公告的课程名

    private String topic; // 公告的主题

    private String content; // 公告的内容

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date publishDate; // 公告发布时间

    private Integer status; // 公告的阅读状态

    private Integer nid; // 公告主键

    public NoticeCourse() {
    }

    public NoticeCourse(String publisher, Integer nid) {
        this.publisher = publisher;
        this.nid = nid;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getNcid() {
        return ncid;
    }

    public void setNcid(Integer ncid) {
        this.ncid = ncid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

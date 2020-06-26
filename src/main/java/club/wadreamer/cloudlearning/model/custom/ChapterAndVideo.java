package club.wadreamer.cloudlearning.model.custom;

import club.wadreamer.cloudlearning.model.auto.Video;

import java.util.List;

/**
 * @ClassName ChapterAndVideo
 * @Description TODO
 * @Author bear
 * @Date 2020/5/2 22:30
 * @Version 1.0
 **/
public class ChapterAndVideo {
    private String chapter;

    private List<Video> videos;

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}

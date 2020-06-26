package club.wadreamer.cloudlearning.model.auto;

import java.io.Serializable;

public class CourseNd implements Serializable {
    private Integer ndId;

    private Integer stId;

    private String ndName;

    private String stName;

    private static final long serialVersionUID = 1L;

    public Integer getNdId() {
        return ndId;
    }

    public void setNdId(Integer ndId) {
        this.ndId = ndId;
    }

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public String getNdName() {
        return ndName;
    }

    public void setNdName(String ndName) {
        this.ndName = ndName == null ? null : ndName.trim();
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }
}
package club.wadreamer.cloudlearning.model.auto;

import java.io.Serializable;

public class CourseSt implements Serializable {
    private Integer stId;

    private String stName;

    private static final long serialVersionUID = 1L;

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName == null ? null : stName.trim();
    }
}
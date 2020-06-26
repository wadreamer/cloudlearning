package club.wadreamer.cloudlearning.model.auto;

import java.io.Serializable;

public class UserRole implements Serializable {
    private String uid;

    private Integer rid;

    public UserRole(String uid, Integer rid) {
        this.uid = uid;
        this.rid = rid;
    }

    private static final long serialVersionUID = 1L;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}
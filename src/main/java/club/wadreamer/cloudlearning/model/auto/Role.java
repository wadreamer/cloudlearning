package club.wadreamer.cloudlearning.model.auto;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer rid;

    private String role;

    private static final long serialVersionUID = 1L;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}
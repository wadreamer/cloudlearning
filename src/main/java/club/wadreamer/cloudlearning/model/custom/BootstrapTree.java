package club.wadreamer.cloudlearning.model.custom;

import java.util.List;

/**
 * @ClassName BootstrapTree
 * @Description TODO
 * @Author bear
 * @Date 2020/3/1 21:41
 * @Version 1.0
 **/
public class BootstrapTree {
    private Integer id;
    private String name;
    private String icon;
    private String url;
    private Integer isBlank;
    private String permKey;
    private Integer visible;
    private List<BootstrapTree> nodes;

    public BootstrapTree(Integer id, String name, String icon, String url, Integer isBlank,
                         String permKey, Integer visible, List<BootstrapTree> nodes) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.isBlank = isBlank;
        this.permKey = permKey;
        this.visible = visible;
        this.nodes = nodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsBlank() {
        return isBlank;
    }

    public void setIsBlank(Integer isBlank) {
        this.isBlank = isBlank;
    }

    public String getPermKey() {
        return permKey;
    }

    public void setPermKey(String permKey) {
        this.permKey = permKey;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public List<BootstrapTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<BootstrapTree> nodes) {
        this.nodes = nodes;
    }
}

package club.wadreamer.cloudlearning.common.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName VideoConfig
 * @Description TODO
 * @Author bear
 * @Date 2020/2/26 22:02
 * @Version 1.0
 **/
@Component
@PropertySource({"classpath:application.properties"})
@ConfigurationProperties(prefix = "project")
public class VideoConfig {

    private String name;
    private String projectName;
    private String artifact;
    private String version;
    private String avatar;
    private Boolean rollVerification;

    //-------------------------------------------------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getRollVerification() {
        return rollVerification;
    }

    public void setRollVerification(Boolean rollVerification) {
        this.rollVerification = rollVerification;
    }

}

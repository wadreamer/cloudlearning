package club.wadreamer.cloudlearning.model.custom;

import club.wadreamer.cloudlearning.model.auto.User;

/**
 * @ClassName Message
 * @Description TODO
 * @Author bear
 * @Date 2020/4/11 16:47
 * @Version 1.0
 **/
public class Message {
    private User from;

    private String message;

    private User to;

    private String time;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

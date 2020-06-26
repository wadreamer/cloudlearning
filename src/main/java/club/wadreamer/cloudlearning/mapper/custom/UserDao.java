package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.auto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User getUserByUsername(String username);//根据用户名获取用户对象

    List<User> search(@Param("condition") String condition, @Param("content") String content,
                      @Param("rid") int rid);
}

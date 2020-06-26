package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //-----------------------------------------------

    User checkUserName(String username);

    User checkEmail(String email);

    User checkPhone(String phone);

    //-----------------------------------------------

    int UseOrNot(@Param("uid") String uid, @Param("isUse") int isUse);

    //-----------------------------------------------

    List<User> getAllByRoleId(@Param("rid") int rid,@Param("uid") String uid);
}
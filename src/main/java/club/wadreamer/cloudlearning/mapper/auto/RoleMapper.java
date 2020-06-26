package club.wadreamer.cloudlearning.mapper.auto;

import club.wadreamer.cloudlearning.model.auto.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

//---------------------------------------------------

    Role selectByUserId(String uid);
}
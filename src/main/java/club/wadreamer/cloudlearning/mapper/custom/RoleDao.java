package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.auto.Role;

public interface RoleDao {
    Role getByUserId(String uid);
}

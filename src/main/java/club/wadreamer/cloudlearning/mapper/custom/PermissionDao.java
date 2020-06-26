package club.wadreamer.cloudlearning.mapper.custom;

import club.wadreamer.cloudlearning.model.auto.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> queryAll();

    List<Permission> queryByUserId(String uid);

    List<Permission> queryByRoleId(int rid);
}

package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.mapper.custom.PermissionDao;
import club.wadreamer.cloudlearning.model.auto.Permission;
import club.wadreamer.cloudlearning.model.custom.BootstrapTree;
import club.wadreamer.cloudlearning.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/13 15:19
 * @Version 1.0
 **/
@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;//自定义Dao

    public BootstrapTree getbooBootstrapTreePerm(String uid) {
        List<BootstrapTree> menuTree = new ArrayList<BootstrapTree>();//菜单树
        List<Permission> userPermissions = getAllPermission(uid);//用户所有权限

        menuTree = getMenuTree(userPermissions,0);

        if(menuTree != null && menuTree.size() == 1){
            return menuTree.get(0);
        }
        return new BootstrapTree(-1,"菜单","fa fa-home","###",0,"",0,menuTree);
    }

    /*
     * @Author bear
     * @Description 根据用户ID, 获取用户所有权限
     * @Date 22:40 2020/3/1
     * @Param [userId]
     * @return java.util.List<club.wadreamer.videos.model.auto.Permission>
     **/
    public List<Permission> getAllPermission(String uid) {
        if (StringUtils.isEmpty(uid)) {
            return permissionDao.queryAll();
        }
        return permissionDao.queryByUserId(uid);
    }

    /*
     * @Author bear
     * @Description 根据用户所有权限, 获取用户的菜单树
     * @Date 22:40 2020/3/1
     * @Param [userPermission, parentId]
     * @return java.util.List<club.wadreamer.videos.model.custom.BootstrapTree>
     **/
    public List<BootstrapTree> getMenuTree(List<Permission> userPermission, int parentId) {
        List<BootstrapTree> treeList = new ArrayList<>();
        List<BootstrapTree> childList = null;

        for (Permission p : userPermission) {
            if (p.getPid() == parentId) {
                if (p.getChildCount() != null && p.getChildCount() > 0) {
                    childList = getMenuTree(userPermission, p.getPerId());
                }
                BootstrapTree bootstrapTree = new BootstrapTree(p.getPerId(), p.getName(), p.getIcon(), p.getUrl(), p.getIsBlank(), p.getPerms(), p.getVisible(), childList);
                treeList.add(bootstrapTree);
                childList = null;
            }
        }
        return treeList.size() > 0 ? treeList : null;
    }
}

package club.wadreamer.cloudlearning.shiro.service;

import club.wadreamer.cloudlearning.mapper.auto.PermissionMapper;
import club.wadreamer.cloudlearning.mapper.auto.RoleMapper;
import club.wadreamer.cloudlearning.mapper.custom.PermissionDao;
import club.wadreamer.cloudlearning.mapper.custom.UserDao;
import club.wadreamer.cloudlearning.model.auto.Permission;
import club.wadreamer.cloudlearning.model.auto.Role;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName MyShiroRealm
 * @Description 用户身份验证核心类
 * @Author bear
 * @Date 2020/2/29 15:30
 * @Version 1.0
 **/
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionDao permissionDao;

    /*
     * @Author bear
     * @Description 认证登录
     * @Date 16:48 2020/2/29
     * @Param [authenticationToken]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @SuppressWarnings("unused")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //执行.login(token)后, 由该方法处理登录认证
        //判断控制器中的UsernamePasswordToken是否注入token
        if(token.getPrincipal() == null){
            return null;
        }
        String username = (String) token.getPrincipal();//获取账号
        String password = new String((char[]) token.getCredentials());//获取密码

        User user = userDao.getUserByUsername(username);//查询数据库记录

        if(user == null){
            return null;
        }else {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user,//用户对象
                    user.getPassword(),//密码
                    getName()//realmName
            );
            return authenticationInfo;
        }
    }

    /*
     * @Author bear
     * @Description 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,即@RequiresPermissions时
     * @Date 16:48 2020/2/29
     * @Param [principalCollection]
     * @return org.apache.shiro.authz.AuthorizationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if(principals == null){
            throw new AuthorizationException("principals should not be null");
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        String id = user.getUid();

        Role role = roleMapper.selectByUserId(id);
        int roleId = role.getRid();//角色id
        authorizationInfo.addRole(role.getRole());//添加角色名

        List<Permission> permissions = permissionDao.queryByRoleId(roleId);//根据角色id查询所有的权限
        for(Permission p : permissions){
            if(StringUtils.isNotEmpty(p.getPerms())){
                authorizationInfo.addStringPermission(p.getPerms());
            }
        }
        return authorizationInfo;
    }

    /*
     * @Author bear
     * @Description 清楚缓存的授权信息
     * @Date 17:12 2020/2/29
     * @Param []
     * @return void
     **/
    public void clearCachedAuthorizationInfo(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

}

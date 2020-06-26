package club.wadreamer.cloudlearning.shiro.util;

import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.shiro.service.MyShiroRealm;
import club.wadreamer.cloudlearning.util.BeanUtils;
import club.wadreamer.cloudlearning.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName ShiroUtils
 * @Description shiro 工具类
 * @Author bear
 * @Date 2020/2/29 15:31
 * @Version 1.0
 **/
public class ShiroUtils {

    private ShiroUtils() {
    }

    /*
     * @Author bear
     * @Description 获取shiro subject
     * @Date 17:19 2020/2/29
     * @Param []
     * @return org.apache.shiro.subject.Subject
     **/
    public static Subject getSubject() {
        //1个请求1个Subject原理:
        //由于ShiroFilterFactoryBean本质是个AbstractShiroFilter过滤器，
        //所以每次请求都会执行doFilterInternal里面的createSubject方法。
        return SecurityUtils.getSubject();
    }

    /*
     * @Author bear
     * @Description 获取登录session
     * @Date 17:19 2020/2/29
     * @Param []
     * @return org.apache.shiro.session.Session
     **/
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /*
     * @Author bear
     * @Description 退出登录
     * @Date 17:19 2020/2/29
     * @Param []
     * @return void
     **/
    public static void logout() {
        getSubject().logout();
    }

    /*
     * @Author bear
     * @Description 获取登录用户model
     * @Date 17:20 2020/2/29
     * @Param []
     * @return club.wadreamer.videos.model.auto.User
     **/
    public static User getUser() {
        User user = null;
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new User();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    /*
     * @Author bear
     * @Description set用户
     * @Date 17:20 2020/2/29
     * @Param [user]
     * @return void
     **/
    public static void setUser(User user) {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();//创建迭代器, 读取用户信息
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        //重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    /*
     * @Author bear
     * @Description 清除授权信息
     * @Date 17:20 2020/2/29
     * @Param []
     * @return void
     **/
    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    /*
     * @Author bear
     * @Description 获取登录用户id
     * @Date 17:20 2020/2/29
     * @Param []
     * @return int
     **/
    public static String getUserId() {
        User user = getUser();
        if (user == null || user.getUid() == null) {
            throw new RuntimeException("用户不存在");
        }
        return user.getUid();
    }

    /*
     * @Author bear
     * @Description 获取登录用户name
     * @Date 17:20 2020/2/29
     * @Param []
     * @return java.lang.String
     **/
    public static String getUserName() {
        User user = getUser();
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user.getUsername();
    }

    /*
     * @Author bear
     * @Description 获取登录用户ip
     * @Date 17:21 2020/2/29
     * @Param []
     * @return java.lang.String
     **/
    public static String getIp() {
        return getSession().getHost();
    }

    /*
     * @Author bear
     * @Description 获取登录用户sessionid
     * @Date 17:21 2020/2/29
     * @Param []
     * @return java.lang.String
     **/
    public static String getSessionId() {
        return String.valueOf(getSession().getId());
    }

}

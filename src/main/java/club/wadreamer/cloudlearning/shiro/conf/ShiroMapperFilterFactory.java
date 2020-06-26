package club.wadreamer.cloudlearning.shiro.conf;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroMapperFilterFactory 配置访问权限
 * @Description TODO
 * @Author bear
 * @Date 2020/2/29 15:30
 * @Version 1.0
 **/
public class ShiroMapperFilterFactory {
    /**
     * anon:例子/admins/**=anon 没有参数，表示可以匿名使用。
     * <p>
     * authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数
     * <p>
     * roles(角色)：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
     * <p>
     * perms（权限）：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
     * <p>
     * rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
     * <p>
     * port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString
     * <p>
     * 是你访问的url里的？后面的参数。
     * <p>
     * authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证
     * <p>
     * ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
     * <p>
     * user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
     */

    public static Map<String,String> shiroFilterMap(){
        //必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 一定要注意顺序,否则就不好使了
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        System.out.println("拦截....");
        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/admin/logout","logout");

        //对所有用户认证,配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        filterChainDefinitionMap.put("/static/**","anon");//静态资源
        filterChainDefinitionMap.put("/API/**","anon");//静态资源
        filterChainDefinitionMap.put("/admin/login","anon");//登录页面
        filterChainDefinitionMap.put("/captcha/**","anon");//验证码
        filterChainDefinitionMap.put("/druid/**","anon");//druid监控
        filterChainDefinitionMap.put("/websocket","anon");//websocket通讯
        filterChainDefinitionMap.put("/","anon");//首页
        filterChainDefinitionMap.put("/index","anon");//后台页面
        filterChainDefinitionMap.put("/quartz/**","anon");//任务调度
        ///此时访问/userInfo/del需要del权限,在自定义Realm中为用户授权。
        //filterChainDefinitionMap.put("/userInfo/del", "perms[\"userInfo:del\"]");

        //其他资源都需要认证  authc 表示需要认证才能进行访问
        filterChainDefinitionMap.put("/**","authc");
        return filterChainDefinitionMap;
    }

}

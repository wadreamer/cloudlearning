package club.wadreamer.cloudlearning.shiro.conf;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import club.wadreamer.cloudlearning.shiro.service.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ShiroConfig 权限配置文件
 * @Description TODO
 * @Author bear
 * @Date 2020/2/29 15:30
 * @Version 1.0
 **/
@Configuration
public class ShiroConfig {

    /*
     * @Author bear
     * @Description 这是shiro的大管家，相当于mybatis里的SqlSessionFactoryBean
     * @Date 16:05 2020/2/29
     * @Param [securityManager]
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     **/
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager){
        //定义ShiroFilterFactoryBean作为返回对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置登录路径
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        //设置成功之后的首页
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        //设置认证不通过时的跳转页面，即错误页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
        //设置权限控制页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(ShiroMapperFilterFactory.shiroFilterMap());

        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    /*
     * @Author bear
     * @Description 配置核心安全事务管理器, 参数均是通过自动注入的方式注入的
     * @Date 16:14 2020/2/29
     * @Param [shiroRealm, cacheManager, manager]
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     **/
    @Bean
    public DefaultWebSecurityManager securityManager(Realm shiroRealm, CacheManager cacheManager, RememberMeManager manager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义的realm
        securityManager.setRealm(shiroRealm);
        //配置缓存管理器
        securityManager.setCacheManager(cacheManager);
        //记住cookie
        securityManager.setRememberMeManager(manager);
        //配置session管理器
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /*
     * @Author bear
     * @Description session过期控制
     * @Date 16:15 2020/2/29
     * @Param []
     * @return org.apache.shiro.session.mgt.DefaultSessionManager
     **/
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
        //设置sessio过期时间3600s
        Long timeout = 60L * 1000 * 60;
        defaultSessionManager.setGlobalSessionTimeout(timeout);
        return defaultSessionManager;
    }

    /*
     * @Author bear
     * @Description 配置MD5加密算法
     * @Date 16:18 2020/2/29
     * @Param []
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /*
     * @Author bear
     * @Description rememberMe 配置
     * @Date 16:23 2020/2/29
     * @Param []
     * @return org.apache.shiro.mgt.RememberMeManager
     **/
    @Bean
    public RememberMeManager rememberMeManager(){
        Cookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24);
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(cookie);
        return manager;
    }

    /*
     * @Author bear
     * @Description 缓存配置
     * @Date 16:25 2020/2/29
     * @Param []
     * @return org.apache.shiro.cache.CacheManager
     **/
    @Bean
    public CacheManager cacheManager(){
        MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
        return  cacheManager;
    }

    /*
     * @Author bear
     * @Description 配置自定义的real, 用于认证和授权
     * @Date 16:36 2020/2/29
     * @Param [hashedCredentialsMatcher]
     * @return org.apache.shiro.realm.AuthorizingRealm
     **/
    @Bean
    public AuthorizingRealm shiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        MyShiroRealm shiroRealm = new MyShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }

    /*
     * @Author bear
     * @Description 启用shiro方言，这样能在页面上使用shiro标签
     * @Date 16:37 2020/2/29
     * @Param []
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     **/
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /*
     * @Author bear
     * @Description 启用shiro注解, 加入@RequiresPermissions注解等等的使用，不加入这个注解不生效
     * @Date 16:39 2020/2/29
     * @Param [securityManager]
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}

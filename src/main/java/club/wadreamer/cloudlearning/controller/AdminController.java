package club.wadreamer.cloudlearning.controller;

import club.wadreamer.cloudlearning.common.base.BaseController;
import club.wadreamer.cloudlearning.common.conf.VideoConfig;
import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Notice;
import club.wadreamer.cloudlearning.model.auto.User;
//import club.wadreamer.cloudlearning.model.custom.BootstrapTree;
//import club.wadreamer.cloudlearning.service.PermissionService;
import club.wadreamer.cloudlearning.model.custom.BootstrapTree;
import club.wadreamer.cloudlearning.service.PermissionService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.JsonUtils;
import club.wadreamer.cloudlearning.util.StringUtils;
import com.google.code.kaptcha.Constants;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private VideoConfig videoConfig;

    @Autowired
    private PermissionService permissionService;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private String prefix = "admin";

    @ApiOperation(value = "首页", notes = "首页")
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        //是否验证通过
        //获取菜单栏
        BootstrapTree bootstrapTree = permissionService.getbooBootstrapTreePerm(ShiroUtils.getUserId());
        request.getSession().setAttribute("bootstrapTree", bootstrapTree);
        request.getSession().setAttribute("sessionUserName", ShiroUtils.getUser().getUsername());
        //获取公告信息
//        List<SysNotice> notices = sysNoticeService.getuserNoticeNotRead(ShiroUtils.getUser(), 0);
//        List<Notice> notices = new ArrayList<>();
//        request.getSession().setAttribute("notices", notices);
//        System.out.println("进入index.html");
        request.getSession().setAttribute("avatar", ShiroUtils.getUser().getAvatarPath());
        return prefix + "/index";
    }

    @ApiOperation(value = "局部刷新区域", notes = "局部刷新区域")
    @GetMapping("/main")
    public String main(ModelMap map) {
        return prefix + "/main";
    }

    @ApiOperation(value = "请求到登录页面login.html", notes = "请求到登录页面login.html")
    @GetMapping("/login")
    public String login(ModelMap modelMap) {
        modelMap.put("RollVerification", videoConfig.getRollVerification());
        try {
            //判断用户是否已有登录信息记录
            if ((null != SecurityUtils.getSubject() && SecurityUtils.getSubject().isAuthenticated()) || SecurityUtils.getSubject().isRemembered()) {
                return "redirect:/" + prefix + "/index";
            } else {
                System.out.println("--进行登录验证..验证开始");

                modelMap.put("RollVerification", videoConfig.getRollVerification());
                System.out.println("V2Config.getRollVerification()>>>" + videoConfig.getRollVerification());
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }

    /**
     * 用户登陆验证
     *
     * @param user
     * @param rcode
     * @param redirectAttributes
     * @param rememberMe
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(User user, String code, RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {
        Boolean yz = false;
        if (videoConfig.getRollVerification()) {//滚动验证
            yz = true;
        } else {//图片验证
            String scode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            yz = StringUtils.isNotEmpty(scode) && StringUtils.isNotEmpty(code) && scode.equals(code);
        }

        //判断验证码
        if (yz) {
            String userName = user.getUsername();
            Subject currentUser = SecurityUtils.getSubject();
            //是否验证通过
            System.out.println("currentUser.isAuthenticated() ====== >" + currentUser.isAuthenticated());
            if (!currentUser.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(userName, user.getPassword());
                try {
                    token.setRememberMe(true);
                    //存入用户,执行登录,调用MyShiroRealm的doGetAuthenticationInfo认证登录
                    currentUser.login(token);
                    if (StringUtils.isNotNull(ShiroUtils.getUser())) {
                        System.out.println("成功");
                        //跳转到 get请求的登陆方法
                        //view.setViewName("redirect:/"+prefix+"/index");
                        //判断用户是否已启用
                        if (ShiroUtils.getUser().getIsUse() > 0) {
                            return AjaxResult.success();
                        } else {
                            ShiroUtils.clearCachedAuthorizationInfo();
                            ShiroUtils.logout();
                            return AjaxResult.error(500, "该用户尚未启用！");
                        }
                    } else {
                        return AjaxResult.error(500, "未知账户");
                    }
                } catch (UnknownAccountException uae) {
                    logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
                    return AjaxResult.error(500, "未知账户");
                } catch (IncorrectCredentialsException ice) {
                    logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
                    return AjaxResult.error(500, "用户名或密码不正确");
                } catch (LockedAccountException lae) {
                    logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
                    return AjaxResult.error(500, "账户已锁定");
                } catch (ExcessiveAttemptsException eae) {
                    logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
                    return AjaxResult.error(500, "用户名或密码错误次数过多");
                } catch (AuthenticationException ae) {
                    //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                    logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
                    ae.printStackTrace();
                    return AjaxResult.error(500, "用户名或密码不正确");
                }
            } else {
                if (StringUtils.isNotNull(ShiroUtils.getUser())) {
                    //跳转到 get请求的登陆方法
                    //view.setViewName("redirect:/"+prefix+"/index");
                    return AjaxResult.success();
                } else {
                    return AjaxResult.error(500, "未知账户");
                }
            }
        } else {
            return AjaxResult.error(500, "验证码不正确!");
        }
    }
}

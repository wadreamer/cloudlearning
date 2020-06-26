package club.wadreamer.cloudlearning.controller;

import club.wadreamer.cloudlearning.common.base.BaseController;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/2 13:55
 * @Version 1.0
 **/
@Controller
public class IndexController extends BaseController {

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        ShiroUtils.clearCachedAuthorizationInfo();
        return "redirect:/login";
    }
}

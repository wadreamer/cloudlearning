package club.wadreamer.cloudlearning.controller;

import club.wadreamer.cloudlearning.common.base.BaseController;
import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Province;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.service.HometownService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.JsonUtils;
import club.wadreamer.cloudlearning.util.MD5Util;
import org.aspectj.weaver.loadtime.Aj;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName SelfController
 * @Description TODO
 * @Author bear
 * @Date 2020/5/1 19:50
 * @Version 1.0
 **/
@Controller
@RequestMapping(("/SelfController"))
public class SelfController extends BaseController {

    private String prefix = "/admin";

    @Autowired
    private HometownService hometownService;

    @GetMapping("/selfInfoView")
    public String updateSelfInfoView(ModelMap modelMap) {
        User user = ShiroUtils.getUser();
        List<Province> provinces = hometownService.getAllProvince();
        modelMap.addAttribute("provinces", provinces);
        modelMap.addAttribute("user", user);
        return prefix + "/self";
    }

    @PostMapping("/self_update")
    @ResponseBody
    public Object selfInfoUpdate(HttpServletRequest request, @RequestParam("image") Object image, User user, String oldPassword, boolean isUpload) {
        String oldPass = ShiroUtils.getUser().getPassword();
        if (!oldPassword.equals("") && !oldPass.equals(MD5Util.encode(oldPassword))) {
            return AjaxResult.error("旧密码不正确，请重新输入！");
        }
        AjaxResult ar = (AjaxResult) edit(image, user, isUpload);
        if ((int) ar.get("code") == 200 && !image.equals("")) {
            System.out.println("修改session中的图片:" + user.getAvatarPath());
            ShiroUtils.getUser().setAvatarPath(user.getAvatarPath());
        }
        return ar;
    }
}

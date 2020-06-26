package club.wadreamer.cloudlearning.controller;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.model.auto.Province;
import club.wadreamer.cloudlearning.model.auto.Role;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.service.HometownService;
import club.wadreamer.cloudlearning.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author bear
 * @Date 2020/3/13 20:05
 * @Version 1.0
 **/
@Controller
@RequestMapping("/UserController")
public class UserController {

    private String prefix = "admin";

    @Value("${project.avatar}")
    private String avatar_path;

    @Autowired
    private UserService userService;

    @Autowired
    private HometownService hometownService;

//    @RequiresPermissions("user:add_view")
    @GetMapping("/user_add_view")
    public String userAddView(ModelMap modelMap) {
        List<Province> provinces = hometownService.getAllProvince();
        modelMap.addAttribute("provinces", provinces);
        return prefix + "/add";
    }

    //    @RequiresPermissions("user:add")
    @PostMapping("/user_add")
    @ResponseBody
    public Object user_add(@RequestParam("image") Object image, User user, String rid) {
        //存在bug,若先选择图片，在提交表单，前端verify不了表单内容
        //检查数据库中是否存在该用户
        User result_username = userService.checkUserName(user.getUsername());
        User result_phone = userService.checkPhone(user.getPhone());
        User result_email = userService.checkEmail(user.getEmail());

        if (result_username != null) {
            return AjaxResult.error(-1, "该用户名已存在，请重新输入");
        }
        if (result_phone != null) {
            return AjaxResult.error(-1, "该联系方式已存在，请重新输入");
        }
        if (result_email != null) {
            return AjaxResult.error(-1, "该邮箱已存在，请重新输入");
        }

        //头像上传路径处理
        MultipartFile file = (MultipartFile) image;
        String fileName = file.getOriginalFilename();//文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//后缀名
        fileName = UUID.randomUUID() + "_" + user.getUsername() + suffixName;//上传后的文件名+后缀名

        File dest = new File(avatar_path + fileName);
        try {
            file.transferTo(dest);//头像写入磁盘
            user.setAvatarPath("/avatar/" + fileName);//设置头像存储到数据库的路径
            int result = userService.insertUserSelective(user, Integer.parseInt(rid));
            if (result > 0) {
                return AjaxResult.success("添加成功!");
            } else {
                return AjaxResult.error(-1, "添加失败，请稍后重试");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.error("操作失败，请稍后重试！");
    }
}

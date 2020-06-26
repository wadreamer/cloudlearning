package club.wadreamer.cloudlearning.common.base;

import club.wadreamer.cloudlearning.common.domain.AjaxResult;
import club.wadreamer.cloudlearning.common.file.FileUtils;
import club.wadreamer.cloudlearning.model.auto.Province;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.service.HometownService;
import club.wadreamer.cloudlearning.service.UserService;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.JsonUtils;
import club.wadreamer.cloudlearning.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import com.fc.test.model.custom.TitleVo;
//import com.fc.test.service.*;

/**
 * web层通用数据处理
 *
 * @author fuce
 * @ClassName: BaseController
 * @date 2018年8月18日
 */
@Controller
public class BaseController {
    @Value("${project.avatar}")
    private String avatar_path;

    @Value("${project.del_avatar}")
    private String del_avatar;

    //系统用户
    @Autowired
    public UserService userService;

    @Autowired
    public HometownService hometownService;

    //系统角色
//	@Autowired
//	public SysRoleService sysRoleService;
//
    //权限
//	@Autowired
//	public SysPermissionService sysPermissionService;

//	//文件上传
//	@Autowired
//	public SysFileService sysFileService;
//
//	//文件存储
//	@Autowired
//	public SysDatasService sysDatasService;
//
//	//文件存储管理表
//	@Autowired
//	public SysFileDatasService sysFileDatasService;
//
//	//日志操作
//	@Autowired
//	public SysOperLogService sysOperLogService;
//
//	//公告
//	@Autowired
//	public SysNoticeService sysNoticeService;
//
//	/*文件上传云库*/
//    @Autowired
//    public QiNiuCloudService qiNiuCloudService;

    public Object useOrNot(String uid, int isUse) {
        if (userService.UseOrNot(uid, isUse) > 0) {
            return AjaxResult.success("操作成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    public Object del(String uid) {
        if (userService.deleteSelective(uid) > 0) {
            return AjaxResult.success("删除成功！");
        } else {
            return AjaxResult.error("操作失败，请稍后重试！");
        }
    }

    public PageInfo<User> getList(int page, int limit, int rid) {
        return userService.getAllByRoleId(page, limit, rid);
    }

    public void edit_view(ModelMap modelMap, String uid){
        User user = userService.getUserByUid(uid);
        List<Province> provinces = hometownService.getAllProvince();
        modelMap.addAttribute("provinces", provinces);
        modelMap.addAttribute("user", user);
    }

    public Object edit(Object image, User user, boolean isUpload) {
        System.out.println(JsonUtils.gsonString(user));
        User old = userService.getUserByUid(user.getUid());

        User result_phone = userService.checkPhone(user.getPhone());
        User result_email = userService.checkEmail(user.getEmail());

        if (result_phone != null && !old.getPhone().equals(user.getPhone())) {
            return AjaxResult.error(-1, "该联系方式已存在，请重新输入");
        }
        if (result_email != null && !old.getEmail().equals(user.getEmail())) {
            return AjaxResult.error(-1, "该邮箱已存在，请重新输入");
        }

        if (isUpload) {
            //修改了头像
            //头像上传路径处理
            MultipartFile file = (MultipartFile) image;
            String fileName = file.getOriginalFilename();//文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//后缀名
            fileName = UUID.randomUUID() + "_" + user.getUsername() + suffixName;//上传后的文件名+后缀名

            File dest = new File(avatar_path + fileName);
            try {
                file.transferTo(dest);//头像写入磁盘
                user.setAvatarPath("/avatar/" + fileName);//设置头像存储到数据库的路径

                int result = userService.updateSelective(user);
                FileUtils.deleteFile(del_avatar + old.getAvatarPath());
                if (result > 0) {
                    ShiroUtils.setUser(user);
                    return AjaxResult.success("修改成功!");
                } else {
                    return AjaxResult.error(-1, "操作失败，请稍后重试");
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //未修改头像
            int result = userService.updateSelective(user);
            if (result > 0) {
                user.setAvatarPath(ShiroUtils.getUser().getAvatarPath());
                ShiroUtils.setUser(user);
                return AjaxResult.success("修改成功!");
            } else {
                return AjaxResult.error(-1, "操作失败，请稍后重试");
            }
        }
        return AjaxResult.error(-1, "操作失败，请稍后重试");
    }


    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public void display(String info) {
        System.out.println("==============" + info + "=====================");
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }


    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message) {
        return AjaxResult.error(code, message);
    }

    /**
     * 返回object数据
     */
    public AjaxResult retobject(int code, Object data) {
        return AjaxResult.successData(code, data);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }


    /**
     * 设置标题通用方法
     * @param model
     */
//    public void setTitle(ModelMap map, TitleVo titleVo){
//    	//标题
//    	map.put("title",titleVo.getTitle());
//    	map.put("parenttitle",titleVo.getParenttitle());
//		//是否打开欢迎语
//    	map.put("isMsg",titleVo.isMsg());
//		//欢迎语
//    	map.put("msgHTML",titleVo.getMsgHtml());
//		//小控件
//    	map.put("isControl",titleVo.isControl());
//		map.put("isribbon", titleVo.isIsribbon());
//    }


}

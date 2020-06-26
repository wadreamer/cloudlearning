package club.wadreamer.cloudlearning.service;

import club.wadreamer.cloudlearning.mapper.auto.UserMapper;
import club.wadreamer.cloudlearning.mapper.auto.UserRoleMapper;
import club.wadreamer.cloudlearning.mapper.custom.UserDao;
import club.wadreamer.cloudlearning.model.auto.Role;
import club.wadreamer.cloudlearning.model.auto.User;
import club.wadreamer.cloudlearning.model.auto.UserRole;
import club.wadreamer.cloudlearning.shiro.util.ShiroUtils;
import club.wadreamer.cloudlearning.util.MD5Util;
import club.wadreamer.cloudlearning.util.SnowflakeIdWorker;
import club.wadreamer.cloudlearning.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author bear
 * @Date 2020/3/13 13:30
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleMapper userRoleMapper;

    //-------------------------------------------------
    public User checkUserName(String username) {
        return userMapper.checkUserName(username);
    }

    public User checkEmail(String email) {
        return userMapper.checkEmail(email);
    }

    public User checkPhone(String phone) {
        return userMapper.checkPhone(phone);
    }
    //-------------------------------------------------

    @Transactional
    public int UseOrNot(String id, int isUse) {
        return userMapper.UseOrNot(id, isUse);
    }

    @Transactional
    public int insertUserSelective(User user, int roleId) {
        String userid = SnowflakeIdWorker.getUUID();
        user.setPassword(MD5Util.encode(user.getPassword()));//密码加密
        user.setUid(userid);//设置用户ID
        user.setIsUse(0);//不启用

        UserRole ur = new UserRole(userid, roleId);

        int result_ur = userRoleMapper.insertSelective(ur);
        int result_user = userMapper.insertSelective(user);

        return (result_ur > 0 & result_user > 0) ? 1 : 0;
    }

    //-------------------------------------------------
    public PageInfo<User> getAllByRoleId(int pageNum, int pageSize, int roleId) {
        System.out.println(pageNum + "========>" + pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.getAllByRoleId(roleId, ShiroUtils.getUserId());
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    @Transactional
    public int deleteSelective(String uid) {
        int result_user = userMapper.deleteByPrimaryKey(uid);
        int result_userRole = userRoleMapper.deleteByUid(uid);

        return (result_user > 0 && result_userRole > 0) ? 1 : 0;
    }

    public PageInfo<User> search(int pageNum, int pageSize, String condition, String content, int roleId) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userDao.search(condition, content, roleId);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    public User getUserByUid(String uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Transactional
    public int updateSelective(User user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(MD5Util.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        int result_user = userMapper.updateByPrimaryKeySelective(user);

        return result_user;
    }


}

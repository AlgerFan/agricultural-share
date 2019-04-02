package org.wlgzs.agricultural_share.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 阿杰
 * @create 2018-07-18 11:33
 * @Description: userService层
 */
public interface UserService {

    /**
     * @Author 阿杰
     * @Description 后台遍历用户
     * @Date 2018/7/18 20:06
     * @Param [page, limit]
     **/
    Page adminUserList(int page, int limit);

    /**
     * @Author 阿杰
     * @Description 后台添加用户 注册
     * @Date 2018/7/19 8:44
     * @Param [user]
     **/
    ResultUtil addUser(User user, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 后台删除单个用户
     * @Date 2018/7/19 8:48
     * @Param [userId]
     **/
    ResultUtil deleteUser(long userId);

    /**
     * @Author 阿杰
     * @Description 后台批量删除用户
     * @Date 2018/7/19 11:16
     * @Param [userIds]
     **/
    ResultUtil batchDeleteUser(String userIds);

    /**
     * @Author 阿杰
     * @Description 根据id查询用户
     * @Date 2018/7/19 11:30
     * @Param [userId]
     **/
    User findUserById(long userId);

    /**
     * @Author 阿杰
     * @Description 修改用户信息
     * @Date 2018/7/19 9:06
     * @Param [user]
     **/
    ResultUtil update(long userId, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 后台按用户名或地址搜索用户
     * @Date 2018/7/19 10:10
     * @Param [keyWord, page, limit]
     **/
    Page findUserList(String keyWord, int page, int limit);

    /**
     * @Author 阿杰
     * @Description 登录
     * @Date 2018/7/19 15:20
     * @Param [userName, userPassword, request]
     **/
    ResultUtil login(String userPhone, String userPassword, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 检查用户名是否存在
     * @Date 2018/7/19 17:25
     * @Param [userName]
     **/
    ResultUtil checkUserName(String userName);

    /**
     * @Description 检查手机号是否重复
     * @Date 2018/7/25 11:14
     * @Param [userPhone]
     **/
    ResultUtil checkUserPhone(String newUserPassword);

    ResultUtil updatePsw(String userPassword, String userPhone, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 用户退出
     * @Date 2018/7/19 15:46
     * @Param [request]
     **/
    ResultUtil cancellation(HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 后台用户退出
     * @Date 2018/7/19 15:47
     * @Param [request]
     **/
    ResultUtil adminCancellation(HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 用户修改头像
     * @Date 2018/7/19 16:49
     * @Param [session, request, myFileName]
     **/
    void headPortrait(HttpSession session, HttpServletRequest request, MultipartFile myFileName) throws IOException;

}

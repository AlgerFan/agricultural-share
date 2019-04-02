package org.wlgzs.agricultural_share.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 阿杰
 * @create 2018-07-18 11:33
 * @Description: 前台用户
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @RequestMapping("toLogin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }
    /**
     * @Description 登录
     * @Date 2018/7/19 15:19
     * @Param [userName, userPassword, request]
     **/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultUtil login(String userPhone, String userPassword, HttpServletRequest request){
        return userService.login(userPhone,userPassword,request);
    }
    @RequestMapping("toRegistered")
    public ModelAndView toRegistered(){
        return new ModelAndView("register");
    }
    /**
     * @Description 注册
     * @Date 2018/7/19 15:37
     * @Param [user]
     **/
    @PostMapping("/registered")
    public ModelAndView addUser(User user,HttpServletRequest request) {
        userService.addUser(user, request);
        return new ModelAndView("redirect:toLogin");
    }
    /**
     * @Description 检查用户名是否存在
     * @Date 2018/7/19 17:25
     * @Param [userName]
     **/
    @PostMapping("/checkUserName")
    public ResultUtil checkUserName(String userName) {
        return userService.checkUserName(userName);
    }

    /**
     * @Description 检查手机号是否重复
     * @Date 2018/7/25 11:14
     * @Param [userPhone]
     **/
    @PostMapping("/checkUserPhone")
    public ResultUtil checkUserPhone(String userPhone) {
        return userService.checkUserPhone(userPhone);
    }
    /**
     * @Description 用户退出
     * @Date 2018/7/19 15:45
     * @Param [request]
     **/
    @RequestMapping("cancellation")
    public ResultUtil cancellation(HttpServletRequest request) {
        return userService.cancellation(request);
    }
    /**
     * @Description 后台用户退出
     * @Date 2018/7/19 15:47
     * @Param [request]
     **/
    @RequestMapping("adminCancellation")
    public ResultUtil adminCancellation(HttpServletRequest request) {
        return userService.adminCancellation(request);
    }
    /**
     * @Description 获得用户的个人信息
     * @Date 2018/7/19 15:51
     * @Param [userId]
     **/
    @RequestMapping(value = "/findByUserId",method = RequestMethod.POST)
    public ResultUtil findByUserId(long userId) {
        User user =  userService.findUserById(userId);
        if (user == null) {
            ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
            resultUtil.setMsg("该用户不存在");
            return resultUtil;
        }
        return new ResultUtil(ResultEnums.FIND);
    }
    @RequestMapping("/set")
    public ModelAndView set(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null) {
            return new ModelAndView("redirect:toLogin");
        }
        return new ModelAndView("set");
    }
    /**
     * @Description 修改密码
     * @Date 2018/7/19 16:16
     * @Param [userId, request]
     **/
    @PostMapping("/updatePsw")
    public ResultUtil updatePsw(String userPassword, String newUserPassword, HttpServletRequest request){
        return userService.updatePsw(userPassword,newUserPassword,request);
    }
    /**
     * @Description 用户修改头像
     * @Date 2018/7/19 16:49
     * @Param [myFileName, session, request]
     **/
    @RequestMapping(value = "/headPortrait",method = RequestMethod.POST)
    public ModelAndView headPortrait(@RequestParam("file") MultipartFile myFileName, HttpSession session,
                           HttpServletRequest request) throws IOException {
        userService.headPortrait(session,request,myFileName);
        return new ModelAndView("set");
    }
}

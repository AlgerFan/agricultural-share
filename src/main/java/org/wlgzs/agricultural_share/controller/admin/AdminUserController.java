package org.wlgzs.agricultural_share.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 阿杰
 * @create 2018-07-18 11:33
 * @Description: 后台用户管理
 */
@RestController
@RequestMapping("admin/user")
public class AdminUserController extends BaseController {

    /**
     * @Description 后台遍历用户
     * @Date 2018/7/18 20:06
     * @Param [page, limit]
     **/
    @RequestMapping("/adminUserList")
    public ModelAndView list(Model model,@RequestParam(value = "page",defaultValue = "0") int page,
                           @RequestParam(value = "limit",defaultValue = "10") int limit) {
        if(page!=0) page--;
        Page pages = userService.adminUserList(page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的总页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        model.addAttribute("users", pages.getContent());//查询的当前页的集合
        return new ModelAndView("usermanage");
    }
    /**
     * @Description 后台增加用户
     * @Date 2018/7/18 20:33
     * @Param [user]
     **/
    @PostMapping("/addUser")
    public ResultUtil addUser(User user,HttpServletRequest request) {
        return userService.addUser(user,request);
    }
    /**
     * @Description 后台删除单个用户
     * @Date 2018/7/19 8:47
     * @Param [userId]
     **/
    @PostMapping("/deleteUser")
    public ResultUtil deleteUser(long userId) {
        System.out.println(userId+"----------");
        return userService.deleteUser(userId);
    }
    /**
     * @Description 后台批量删除用户
     * @Date 2018/7/19 11:16
     * @Param [userIds]
     **/
    @RequestMapping(value = "/batchDelete",method = RequestMethod.POST)
    public ResultUtil batchDeleteUser(String userIds){
        return userService.batchDeleteUser(userIds);
    }
    /**
     * @Description 后台根据id查询用户
     * @Date 2018/7/19 11:30
     * @Param [userId]
     **/
    @RequestMapping(value = "/findByUserId",method = RequestMethod.POST)
    public ResultUtil findByUserId(long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
            resultUtil.setMsg("该用户不存在");
            return resultUtil;
        }
        return new ResultUtil(ResultEnums.FIND);
    }
    /**
     * @Description 后台修改用户信息
     * @Date 2018/7/19 9:06
     * @Param [user]
     **/
    @PostMapping("/update")
    public ResultUtil update(long userId, HttpServletRequest request){
        return userService.update(userId,request);
    }
    /**
     * @Description 后台按用户名或地址搜索用户
     * @Date 2018/7/19 10:10
     * @Param [keyWord, page, limit]
     **/
    @GetMapping("/findUserList")
    public ModelAndView findUserList(Model model, String keyWord,@RequestParam(value = "page",defaultValue = "0") int page,
                                  @RequestParam(value = "limit",defaultValue = "8") int limit) {
        if(page != 0) page--;
        Page pages =  userService.findUserList(keyWord,page, limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<User> users = pages.getContent();
        model.addAttribute("users", users);//查询的当前页的集合
        return new ModelAndView("finduser");
    }
}

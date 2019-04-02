package org.wlgzs.agricultural_share.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.agricultural_share.dao.UserRepository;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.service.UserService;
import org.wlgzs.agricultural_share.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 阿杰
 * @create 2018-07-18 11:33
 * @Description: user实现层
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public Page adminUserList(int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "userId");
        Pageable pageable = PageRequest.of(page, limit, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public ResultUtil addUser(User user, HttpServletRequest request) {
        user.setUserAvatar("/upload/headPortrait/default.jpg");
        userRepository.save(user);
        return new ResultUtil(ResultEnums.SAVE);
    }

    @Override
    public ResultUtil deleteUser(long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            if (!user.getUserRole().equals("") && user.getUserRole() != null) {
                if (user.getUserRole() == 1) {
                    ResultUtil resultUtil = new ResultUtil(ResultEnums.UNDELETE);
                    resultUtil.setMsg("抱歉，您无权限删除管理员");
                    return resultUtil;
                } else {
                    UploadUtil uploadUtil = new UploadUtil();
                    uploadUtil.deleteFile(user.getUserAvatar());
                    userRepository.deleteById(userId);
                    return new ResultUtil(ResultEnums.DELETE);
                }
            }
        }
        return new ResultUtil(ResultEnums.UNDELETE);
    }

    @Override
    public ResultUtil batchDeleteUser(String userIds) {
        if (userIds == null) {
            return new ResultUtil(ResultEnums.UNDELETE);
        }
        UploadUtil uploadUtil = new UploadUtil();
        long[] ids = IdsUtil.IdsUtils(userIds);
        List<User> users = userRepository.findByIds(ids);
        for (User user : users) {
            uploadUtil.deleteFile(user.getUserAvatar());
        }
        userRepository.deleteByIds(ids);
        return new ResultUtil(ResultEnums.DELETE);
    }

    @Override
    public User findUserById(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public ResultUtil update(long userId, HttpServletRequest request) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
        Map<String, String[]> properties = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (user.getUserName().equals("") || user.getUserName() == null || user.getUserPassword().equals("") ||
                user.getUserPassword() == null || user.getUserPhone().equals("") || user.getUserPhone() == null) {
            resultUtil.setMsg("用户名或密码或手机号不能为空");
            return resultUtil;
        }
        if (user.getUserName().length() < 2 || user.getUserName().length() > 10 || user.getUserPassword().length() < 6 ||
                user.getUserPassword().length() > 16 || user.getUserPhone().length() != 11) {
            resultUtil.setMsg("用户名或密码或手机号不符合要求");
            return resultUtil;
        }
        User useTwo = userRepository.findById(userId);
        List<User> userOnes = userRepository.checkUserNames(request.getParameter("userName"));
        for (int i = 0; i < userOnes.size(); i++) {
            if (userOnes.get(i).getUserName().equals(useTwo.getUserName())) {
                userOnes.remove(i);
            }
        }
        for (int i = 0; i < userOnes.size(); i++) {
            System.out.println(userOnes.get(i)+"-----");
        }
        if (userOnes.size()!=0) {
            resultUtil.setMsg("该用户名已经存在");
            return resultUtil;
        }
        if (useTwo != null) {
            user.setUserId(useTwo.getUserId());
            user.setUserAvatar(useTwo.getUserAvatar());
            //user.setUserPhone(useTwo.getUserPhone());
            userRepository.saveAndFlush(user);
            return new ResultUtil(ResultEnums.UPDATE);
        }
        return new ResultUtil(ResultEnums.UNUPDATE);
    }

    @Override
    public Page findUserList(String keyWord, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "userId");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Specification<User> specification = new PageUtil<User>(keyWord).getPage("userName", "userAddress");
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public ResultUtil login(String userPhone, String userPassword, HttpServletRequest request) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
        if (userPhone==null || userPassword==null) {
            resultUtil.setMsg("手机号或密码不能为空！");
            return resultUtil;
        }
        HttpSession session = request.getSession(true);
        List<User> users = userRepository.findAll();
        User user = null;
        for (User user1 : users) {
            if (userPhone.equals(user1.getUserPhone()) && userPassword.equals(user1.getUserPassword())) {
                user = user1;
                break;
            }
        }
        if (user != null) {
            if (user.getUserRole() == 0) {
                session.setMaxInactiveInterval(60 * 20);
                session.setAttribute("user", user);
                resultUtil.setMsg("用户登录成功");
                return resultUtil;
            }
            if (user.getUserRole() == 1) {
                session.setMaxInactiveInterval(60 * 20);
                session.setAttribute("adminUser", user);
                resultUtil.setMsg("管理员登录成功");
                return resultUtil;
            }
        }
        resultUtil.setMsg("账号或密码错误");
        return resultUtil;
    }

    @Override
    public ResultUtil checkUserName(String userName) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.SUCCESS);
        if (userName.equals("")) {
            resultUtil.setMsg("用户名不能为空");
            return resultUtil;
        }
        User user = userRepository.checkUserName(userName);
        if (user == null) {
            resultUtil.setMsg("该用户名可以使用");
        } else {
            resultUtil.setMsg("该用户名已经存在");
        }
        return resultUtil;
    }

    @Override
    public ResultUtil checkUserPhone(String userPhone) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.SUCCESS);
        if (userPhone.equals("")) {
            resultUtil.setMsg("手机号不能为空");
            return resultUtil;
        }
        User user = userRepository.checkPhone(userPhone);
        if (user == null) {
            resultUtil.setMsg("该手机号可以使用");
        } else {
            resultUtil.setMsg("该手机号已经存在");
        }
        return resultUtil;
    }

    @Override
    public ResultUtil updatePsw(String userPassword, String newUserPassword, HttpServletRequest request) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.UPDATE);
        if (userPassword.equals("")) {
            resultUtil.setMsg("旧密码不能为空");

            return resultUtil;
        }
        if (newUserPassword.equals("")) {
            resultUtil.setMsg("新密码不能为空");
            return resultUtil;
        }
        if (newUserPassword.length()<6) {
            resultUtil.setMsg("新密码设置过短，请重新输入");
            return resultUtil;
        }
        if (newUserPassword.equals(userPassword)) {
            resultUtil.setMsg("新密码不能与旧密码相同，请重新输入");
            return resultUtil;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (!userPassword.equals(user.getUserPassword())) {
            resultUtil.setMsg("验证密码错误，请重新输入");
            return resultUtil;
        }
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        user.setUserPassword(newUserPassword);
        userRepository.save(user);
        return resultUtil;
    }

    @Override
    public ResultUtil cancellation(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
            return new ResultUtil(ResultEnums.SUCCESS);
        }
        return new ResultUtil(ResultEnums.UNKONW_ERROR);
    }

    @Override
    public ResultUtil adminCancellation(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("adminUser") != null) {
            session.removeAttribute("adminUser");
            return new ResultUtil(ResultEnums.SUCCESS);
        }
        return new ResultUtil(ResultEnums.UNKONW_ERROR);
    }

    @Override
    public void headPortrait(HttpSession session, HttpServletRequest request, MultipartFile myFileName) throws IOException {
        String fileName = myFileName.getOriginalFilename();
        User user = (User) session.getAttribute("user");
        if (CheckImage.verifyImage(fileName)) {
            String realName;
            String userAvatar;
            String fileNameExtension = fileName.substring(fileName.indexOf("."));
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;

            // "/headPortrait"是你自己定义的上传目录
            String path = "/upload/headPortrait/" + realName;
            UploadUtil uploadUtil = new UploadUtil();
            uploadUtil.saveFile(myFileName, path);
            userAvatar = "/upload/headPortrait/" + realName;
            uploadUtil.deleteFile(user.getUserAvatar());
            user.setUserAvatar(userAvatar);
            userRepository.saveAndFlush(user);
            session.setMaxInactiveInterval(60 * 20);
            session.setAttribute("user", user);
        }
    }

}

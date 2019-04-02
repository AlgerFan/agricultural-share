package org.wlgzs.agricultural_share.base;

import org.wlgzs.agricultural_share.dao.*;
import org.wlgzs.agricultural_share.service.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author 阿杰
 * @description 简化控制层代码
 */
public abstract class BaseController implements Serializable {

    //自动注入业务层
    @Resource
    protected UserService userService;
    @Resource
    protected CategoryService categoryService;
    @Resource
    protected MessageService messageService;
    @Resource
    protected BrowseService browseService;
    @Resource
    protected CollectService collectService;
    @Resource
    protected HeadlineService headlineService;

    @Resource
    protected HttpSession session;

    @Resource
    protected UserRepository userRepository;
    @Resource
    protected MessageRepository messageResponsitory;
    @Resource
    protected BrowseRepository browseResponsitory;
    @Resource
    protected CollectRepository collectResponsitory;
    @Resource
    protected CategoryRepository categoryRepository;

    protected final int adminRole = 1;
    protected String RootUrl = "/upload/message/";
    protected final String needType = "用户需求";
}

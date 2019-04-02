package org.wlgzs.agricultural_share.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Headline;
import org.wlgzs.agricultural_share.entity.Message;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {
    /**
     * @Description 发布信息
     * @Date 2018/7/31 21:11
     * @Param [message, request, response]
     **/
    @RequestMapping
    public void createMessage(Message message, HttpServletRequest request, List<MultipartFile> files, HttpServletResponse response) {
        boolean b = messageService.addMessage(request, message, files);
        try {
            response.sendRedirect("/message/person");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description 需求发布
     * @Date 2018/7/31 21:11
     * @Param [message, request, response]
     **/
    @RequestMapping("/createDemand")
    public void createDemand(Message message, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            messageService.createDemand(request, message,user.getUserId());
        }
        try {
            response.sendRedirect("/message/person");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description 删除发布信息
     * @Date 2018/7/31 21:11
     * @Param [message, request, response]
     **/
    @DeleteMapping
    public ResultUtil deleteMessage(HttpServletRequest request) {
        return new ResultUtil(messageService.deleteMessage(request));
    }
    /**
     * @Description 遍历所有发布信息
     * @Date 2018/7/31 21:12
     * @Param [model]
     **/
    @RequestMapping("/allMessageList")
    public ModelAndView allMessageList(Model model){
        List<Message> messages = messageService.allMessageList();
        model.addAttribute("messages",messages);
        return new ModelAndView("list");
    }
    /**
     * @Description 搜索发布信息
     * @Date 2018/7/31 21:12
     * @Param [model]
     **/
    @RequestMapping("/searchMessageList")
    public ModelAndView searchMessageList(Model model,String title){
        List<Message> messages = messageService.searchMessageList(title);
        model.addAttribute("messages",messages);
        return new ModelAndView("list");
    }

    @PostMapping("/delete")
    public ResultUtil deleteMessages(int[] messageIds, HttpSession session) {
        return new ResultUtil(messageService.deleteMessage(messageIds, session));
    }

    @GetMapping("/search")
    public ResultUtil searchMessage(HttpServletRequest request) {
        Page<Message> messages = messageService.serchMessage(request);
        if (messages == null || messages.getContent().size() == 0) return new ResultUtil(ResultEnums.UNFIND);
        Map<String, Object> map = new HashMap<>();
        map.put("browses", messages.getContent());
        map.put("pageNumber", messages.getNumber());
        map.put("pageSize", messages.getSize());
        return new ResultUtil(ResultEnums.FIND, map);
    }

    /**
     * 我的发布
     * @param request
     * @param model
     */
    @GetMapping("/person")
    public ModelAndView toMyMessage(HttpServletRequest request,Model model) {
        if (request.getSession().getAttribute("user") == null) return new ModelAndView("index");
        request.setAttribute("isMine", "true");
        //头条
        List<Headline> headlineList = headlineService.findAll();
        model.addAttribute("headlineList",headlineList);
        //个人发布信息
        Page<Message> messages = messageService.serchMessage(request);
        request.setAttribute("page", messages.getNumber());
        request.setAttribute("pageSize", messages.getSize());
        request.setAttribute("messages", messages.getContent());
        return new ModelAndView("announcement");
    }

    /**
     * 推荐农资
     * @param request
     * @param model
     */
    @GetMapping("/recommended")
    public ModelAndView recommended(HttpServletRequest request,Model model) {
        if (request.getSession().getAttribute("user") == null) return new ModelAndView("index");
        model.addAttribute("isMine", "true");
        //头条
        List<Headline> headlineList = headlineService.findAll();
        model.addAttribute("headlineList",headlineList);
        //个性推荐信息
        List<Message> messages = messageService.recommended(request);
        model.addAttribute("messages", messages);
        return new ModelAndView("recommended");
    }

    @GetMapping("/person/load")
    public ResultUtil load(HttpServletRequest request) {
        request.setAttribute("isMine", "true");
        Page<Message> messages = messageService.serchMessage(request);
        if (messages.getContent().size() == 0) return new ResultUtil(ResultEnums.UNFIND);
        request.setAttribute("page", messages.getNumber());
        request.setAttribute("pageSize", messages.getSize());
        request.setAttribute("messages", messages.getContent());
        return new ResultUtil(ResultEnums.FIND);
    }

}

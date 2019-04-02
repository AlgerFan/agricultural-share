package org.wlgzs.agricultural_share.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Browse;
import org.wlgzs.agricultural_share.entity.Collect;
import org.wlgzs.agricultural_share.entity.Message;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/browse")
public class BrowseController extends BaseController {

    //创建足迹
    @GetMapping
    public ModelAndView read(Model model,@RequestParam("messageId") int messageId, HttpServletRequest request) {
        if (browseService.read(messageId, request));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            Collect collect = collectService.findUserIdAndMessageId(messageId,user.getUserId());
            if(collect!=null){
                model.addAttribute("msg","1");
            } else {
                model.addAttribute("msg","0");
            }
        }
        return new ModelAndView("details");
    }

    /**
     * @Description 查找用户所有足迹
     * @Date 2018/7/30 20:31
     * @Param [model, request]
     **/
    @RequestMapping("/findUserAllBrowse")
    public ModelAndView findUserAllBrowse(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        /*User user = new User();
        user.setUserId(35);*/
        if (user != null) {
            List<Browse> browses = browseService.findUserAllBrowse(user.getUserId());
            System.out.println(browses+"--------------");
            if (browses != null && browses.size()!=0) {
                int[] messageId = new int[browses.size()];
                for (int i = 0; i < browses.size(); i++) {
                    messageId[i] = browses.get(i).getMessageId();
                }
                List<Message> messages = messageService.findMessageByIds(messageId);
                //数据库设计原因，足迹表里没有图片等信息，所以页面展示商品数据，把足迹id放在了商品集合中，以便删除足迹
                for (int i = 0; i < messages.size(); i++) {
                    messages.get(i).setClicks(browses.get(i).getBrowseId());
                }
                model.addAttribute("messages", messages);
            }
        }
        return new ModelAndView("foot");
    }
    /**
     * @Description 足迹移至收藏
     * @Param [messageId, browseId]
     **/
    @RequestMapping("/removeCollect")
    public ResultUtil removeCollect(int messageId,int browseId,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        collectService.removeCollect(messageId,browseId,user.getUserId());
        return new ResultUtil(ResultEnums.SUCCESS);
    }

    //删除足迹
    @RequestMapping("/deleteBrowse")
    public ResultUtil deleteBrowse(@RequestParam("browseId") int browseId, HttpSession session) {
        return new ResultUtil(browseService.deleteBrowse(browseId, session));
    }

    /**
     * @Description 搜索用户足迹
     * @Date 2018/7/30 20:53
     * @Param [model, keyWord, page, limit]
     **/
    @RequestMapping("/searchUserBrowse")
    public ModelAndView searchUserBrowse(Model model, String keyWord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
        /*User user = new User();
        user.setUserId(35);*/
            List<Browse> browses = browseService.searchUserBrowse(user.getUserId(), keyWord);
            int[] messageId = new int[browses.size()];
            for (int i = 0; i < browses.size(); i++) {
                messageId[i] = browses.get(i).getBrowseId();
            }
            List<Message> messages = messageService.findMessageByIds(messageId);
            //数据库设计原因，足迹表里没有图片等信息，所以页面展示商品数据，把足迹id放在了商品集合中，以便删除足迹
            for (int i = 0; i < messages.size(); i++) {
                messages.get(i).setClicks(browses.get(i).getMessageId());
            }
            model.addAttribute("messages", messages);
        }
        return new ModelAndView("");
    }

    @PostMapping("/delete")
    public ResultUtil deleteBrowses(int[] browseIds, HttpSession session) {
        return new ResultUtil(browseService.deleteBrowse(browseIds, session));
    }

    @RequestMapping("/search")
    public ResultUtil searchBrowse(HttpServletRequest request) {
        Page<Browse> browses = browseService.seeBrowseByPage(request);
        System.out.println(browses.getContent() + "--------------");
        if (browses == null || browses.getContent().size() == 0) return new ResultUtil(ResultEnums.UNFIND);
        Map map = new HashMap();
        map.put("browses", browses.getContent());
        map.put("pageNumber", browses.getNumber());
        map.put("pageSize", browses.getSize());
        return new ResultUtil(ResultEnums.FIND, map);
    }
}

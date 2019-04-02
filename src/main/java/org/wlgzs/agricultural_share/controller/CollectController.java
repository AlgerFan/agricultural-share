package org.wlgzs.agricultural_share.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
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
@RequestMapping("/collect")
public class CollectController extends BaseController {
    //添加收藏
    @PostMapping
    public ResultUtil createCollect(int messageId, HttpSession session) {
        return new ResultUtil(collectService.addCollect(messageId, session));
    }

    //删除收藏
    @RequestMapping("/deleteCollect")
    public ResultUtil deleteCollect(@RequestParam("collectId") int collectId) {
        return new ResultUtil(collectService.deleteCollect(collectId, session));
    }

    /**
     * @Description 遍历用户收藏
     * @Date 2018/7/31 8:27
     * @Param [model, request]
     **/
    @RequestMapping("/userCollect")
    public ModelAndView userCollect(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Collect> collects = collectService.userCollect(user.getUserId());
            System.out.println(collects+"-------------");
            if (collects.size()!=0) {
                int[] messageId = new int[collects.size()];
                for (int i = 0; i < collects.size(); i++) {
                    messageId[i] = collects.get(i).getMessageId();
                }
                List<Message> messages = messageService.findMessageByIds(messageId);
                //数据库设计原因，收藏表里没有图片等信息，所以页面展示商品数据，把收藏id放在了商品集合中，以便删除收藏
                for (int i = 0; i < messages.size(); i++) {
                    messages.get(i).setClicks(collects.get(i).getCollectId());
                }
                model.addAttribute("messages", messages);
            }
        }
        return new ModelAndView("collection");
    }

    /**
     * @Description 查找用户收藏
     * @Date 2018/7/31 8:32
     * @Param [model, request]
     **/
    @RequestMapping("/findUserCollect")
    public ModelAndView findUserCollect(String keyWord, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        /*User user = new User();
        user.setUserId(35);*/
        if (user != null) {
            List<Collect> collects = collectService.findUserCollect(user.getUserId(), keyWord);
            int[] messageId = new int[collects.size()];
            for (int i = 0; i < collects.size(); i++) {
                messageId[i] = collects.get(i).getMessageId();
            }
            List<Message> messages = messageService.findMessageByIds(messageId);
            //数据库设计原因，收藏表里没有图片等信息，所以页面展示商品数据，把收藏id放在了商品集合中，以便删除收藏
            for (int i = 0; i < messages.size(); i++) {
                messages.get(i).setClicks(collects.get(i).getCollectId());
            }
            model.addAttribute("messages", messages);
        }
        return new ModelAndView("");
    }


    @PostMapping("/deletes")
    public ResultUtil delteCollect(int[] collectIds, HttpSession session) {
        return new ResultUtil(collectService.deleteCollect(collectIds, session));
    }

    @GetMapping("/search")
    public ResultUtil searchCollect(HttpServletRequest request) {
        Page<Collect> collects = collectService.seeCollectByPage(request);
        if (collects == null || collects.getContent().size() == 0) return new ResultUtil(ResultEnums.UNFIND);
        Map map = new HashMap();
        map.put("browses", collects.getContent());
        map.put("pageNumber", collects.getNumber());
        map.put("pageSize", collects.getSize());
        return new ResultUtil(ResultEnums.FIND, map);
    }
}

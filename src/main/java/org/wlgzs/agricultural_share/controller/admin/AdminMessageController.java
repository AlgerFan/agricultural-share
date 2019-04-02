package org.wlgzs.agricultural_share.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Message;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminMessageController extends BaseController {

    /**
     * @Description 后台遍历发布信息
     * @Param
     **/
    @RequestMapping("/findAllMessage")
    public ModelAndView findAllMessage(Model model,@RequestParam(value = "page",defaultValue = "0") int page,
                                       @RequestParam(value = "limit",defaultValue = "8") int limit) {
        if(page != 0) page--;
        Page pages =  messageService.findAllMessage(page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Message> messages = pages.getContent();
        model.addAttribute("messages", messages);//查询的当前页的集合
        return new ModelAndView("admindemand");
    }
    /**
     * @Description 后台删除发布信息
     * @Param
     **/
    @RequestMapping("/deleteMessage") //需要传messageId
    public ResultUtil deleteMessage(HttpServletRequest request) {
        return new ResultUtil(messageService.deleteMessage(request));
    }
    /**
     * @Description 后台按标题搜索发布信息
     * @Param [keyWord, page, limit]
     **/
    @RequestMapping("/findMessages") //需要传搜索词
    public ModelAndView findMessages(Model model, String keyWord,@RequestParam(value = "page",defaultValue = "0") int page,
                                     @RequestParam(value = "limit",defaultValue = "8") int limit) {
        if(page != 0) page--;
        Page pages =  messageService.findMessages(keyWord,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Message> messages = pages.getContent();
        model.addAttribute("messages", messages);//查询的当前页的集合
        return new ModelAndView("finddemand");
    }

}

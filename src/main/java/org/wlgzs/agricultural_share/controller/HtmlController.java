package org.wlgzs.agricultural_share.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Category;
import org.wlgzs.agricultural_share.entity.Headline;
import org.wlgzs.agricultural_share.entity.Message;
import sun.nio.cs.ext.MS874;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class HtmlController extends BaseController {

    //跳转主页
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/index");
    }
    @GetMapping("/index")
    public ModelAndView toIndex(Model model) {
        List<Message> messages = messageService.findDemand();
        model.addAttribute("messages",messages);
        List<Message> messagesTwo = messageService.index();
        model.addAttribute("messagesTwo", messagesTwo);
        //头条
        List<Headline> headlineList = headlineService.findAll();
        model.addAttribute("headlineList",headlineList);
        return new ModelAndView("index");
    }
    //跳转到发布信息页面
    @GetMapping("/message")
    public ModelAndView toMessage(Model model){
        Page<Category> pages = categoryService.getCategoryList(0, 100);
        List<Category> categories = pages.getContent();
        model.addAttribute("categories", categories);//查询的当前页的集合
        return new ModelAndView("own");
    }

    @GetMapping("/changemanage")
    public ModelAndView toChangeManage(){
        return new ModelAndView("changemanage");
    }

    //跳转至用户需求
    @GetMapping("/needkind")
    public ModelAndView toNeedKind(Model model){
        List<Category> categoryOne =  categoryService.findOneCategory();
        model.addAttribute("categoryOne",categoryOne);
        Page<Category> pages = categoryService.getCategoryList(0, 100);
        List<Category> categories = pages.getContent();
        List<Category> categoryTwo =  new ArrayList<>();
        for (Category category : categories) {
            if (category.getParentId() != 0) {
                categoryTwo.add(category);
            }
        }
        model.addAttribute("categoryTwo",categoryTwo);
        return new ModelAndView("needkind");
    }
    /**
     * @Description 根据分类查找发布信息
     * @Date 2018/7/31 18:04
     * @Param [category]
     **/
    @RequestMapping("/findDemandByCategory")
    public ModelAndView findDemandByCategory(String category,Model model){
        List<Message> messages = messageService.findDemandByCategory(category);
        model.addAttribute("messages",messages);
        return new ModelAndView("needs");
    }
    /**
     * @Description 主页按照一级分类搜索发布信息
     * @Date 2018/7/31 21:20
     * @Param [model, parentType]
     **/
    @RequestMapping("/homeSearchMassage")
    public ModelAndView homeSearchMassage(Model model,String parentType){
        List<Message> messages = messageService.homeSearchMassage(parentType);
        model.addAttribute("messages",messages);
        return new ModelAndView("list");
    }

    //跳转至后台主页
    @GetMapping("/adminindex")
    public ModelAndView toadminIndex(){
        return new ModelAndView("adminindex");
    }

    @GetMapping("/collect")
    public ModelAndView tocollect(){
        return new ModelAndView("collection");
    }

    //跳转至后台添加用户
    @GetMapping("/adduser")
    public ModelAndView toAdduser(){
        return new ModelAndView("adduser");
    }

    //跳转至后台需求
    @GetMapping("/admindemand")
    public ModelAndView toadmindemand(){
        return new ModelAndView("admindemand");
    }

    @GetMapping("/foot")
    public ModelAndView toFoot(){
        return new ModelAndView("foot");
    }

}

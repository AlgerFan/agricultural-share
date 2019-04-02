package org.wlgzs.agricultural_share.controller.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Headline;
import org.wlgzs.agricultural_share.util.ResultUtil;

import java.util.List;

@RestController
@RequestMapping("/headline")
public class AdminHeadlineController extends BaseController {

    /**
     *
     * 功能描述: 增加头条
     *
     * @param: headline
     * @date: 2018/11/27 9:27
     */
    @PostMapping("/")
    public ResultUtil addHeadline(Headline headline){
        return headlineService.addHeadline(headline);
    }

    /**
     * 功能描述: 删除头条
     * @param headlineId
     * @return
     */
    @DeleteMapping("/{headlineId}")
    public ResultUtil deleteById(@PathVariable int headlineId){
        return headlineService.deleteById(headlineId);
    }

    /**
     * 功能描述: 查询所有头条
     * @param model
     * @return
     */
    @GetMapping("/")
    public ModelAndView findAll(Model model){
        List<Headline> headlineList = headlineService.findAll();
        model.addAttribute("headlineList",headlineList);
        return new ModelAndView("headline");
    }
}

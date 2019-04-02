package org.wlgzs.agricultural_share.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Category;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 阿杰
 * @create 2018-07-20 9:44
 * @Description:
 */
@RestController
@RequestMapping("admin/category")
public class CategoryController extends BaseController {

    /**
     * @Author 阿杰
     * @Description 遍历所有分类
     * @Date 2018/7/20 10:38
     * @Param [page, limit]
     **/
    @RequestMapping(value = "/productCategoryList")
    public ModelAndView category(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "limit", defaultValue = "8") int limit) {
        if (page != 0) page--;
        Page<Category> pages = categoryService.getCategoryList(page, limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Category> categories = pages.getContent();
        model.addAttribute("categories", categories);//查询的当前页的集合
        return new ModelAndView("classify");
    }
    /**
     * @Author 阿杰
     * @Description 跳转至添加一级分类
     * @Param []
     **/
    @RequestMapping(value = "/toAddOneCategory")
    public ModelAndView toAddOneCategory() {
        return new ModelAndView("addOneProduct");
    }
    /**
     * @Author 阿杰
     * @Description 添加一级分类
     * @Date 2018/7/20 10:50
     * @Param [category, request]
     **/
    @RequestMapping(value = "/addOneCategory",method = RequestMethod.POST)
    public ResultUtil addOneCategory(Category category,HttpServletRequest request) {
        return categoryService.addOneCategory(category,request);
    }
    /**
     * @Author 阿杰
     * @Description 查询一级分类
     * @Date 2018/7/20 11:03
     * @Param []
     **/
    @RequestMapping(value = "/findOneCategory")
    public ModelAndView findOneCategory(Model model) {
        List<Category> categories =  categoryService.findOneCategory();
        model.addAttribute("categories",categories);
        return new ModelAndView("issue");
    }
    @RequestMapping(value = "/twoCategory")
    public ModelAndView twoCategory(Model model) {
        List<Category> categories =  categoryService.twoCategory();
        model.addAttribute("categories",categories);
        return new ModelAndView("addteoproduct");
    }
    /**
     * @Author 阿杰
     * @Description 根据父类名查询二级分类
     * @Date 2018/7/20 11:03
     * @Param []
     **/
    @RequestMapping(value = "/findTwoCategory",method = RequestMethod.POST)
    public ResultUtil findTwoCategory(String parentName) {
        return categoryService.findTwoCategory(parentName);
    }
    /**
     * @Author 阿杰
     * @Description 跳转至添加二级分类
     * @Param []
     **/
    @RequestMapping(value = "/toAddTwoCategory")
    public ModelAndView toAddTwoCategory(Model model) {
        List<Category> categories =  categoryService.findOneCategory();
        model.addAttribute("categories",categories);
        return new ModelAndView("addTwoProduct");
    }
    /**
     * @Author 阿杰
     * @Description 添加二级分类
     * @Date 2018/7/20 10:57
     * @Param [category, request]
     **/
    @RequestMapping(value = "/addTwoCategory",method = RequestMethod.POST)
    public ResultUtil addTwoCategory(Category category,HttpServletRequest request) {
        return categoryService.addTwoCategory(category,request);
    }
    /**
     * @Author 阿杰
     * @Description 删除分类
     * @Date 2018/7/20 15:29
     * @Param [categoryId]
     **/
    @PostMapping("/deleteCategory")
    public ResultUtil deleteCategory(int categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
    //    跳转至修改一级分类
    @RequestMapping("/toOneUpdate")
    public ModelAndView toOneUpdate(int categoryId,Model model){
        Category category = categoryService.findByCategoryId(categoryId);
        model.addAttribute("category",category);
        return new ModelAndView("changeOneClassify");
    }
    //跳转至修改二级分类
    @RequestMapping("/toTwoUpdate")
    public ModelAndView toTwoUpdate(int categoryId,Model model){
        Category category = categoryService.findByCategoryId(categoryId);
        model.addAttribute("category",category);
        return new ModelAndView("changeTwoClassify");
    }

    /**
     * @Author 阿杰
     * @Description 修改二级分类
     * @Date 2018/7/20 16:22
     * @Param [categoryId, request]
     **/
    @PostMapping("/update")
    public ResultUtil update(int categoryId, HttpServletRequest request){
        return categoryService.update(categoryId,request);
    }
    /**
     * @Author 阿杰
     * @Description 后台按分类名搜索分类
     * @Date 2018/7/20 16:31
     * @Param [keyWord, page, limit]
     **/
    @RequestMapping("/search")
    public ModelAndView findCategoryList(Model model,String keyWord,@RequestParam(value = "page",defaultValue = "0") int page,
                                   @RequestParam(value = "limit",defaultValue = "10") int limit) {
        if(page != 0) page--;
        Page pages = categoryService.findCategoryList(keyWord,page, limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Category> categories = pages.getContent();
        model.addAttribute("categories", categories);//查询的当前页的集合
        return new ModelAndView("classifySearch");
    }

}

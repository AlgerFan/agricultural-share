package org.wlgzs.agricultural_share.service;

import org.springframework.data.domain.Page;
import org.wlgzs.agricultural_share.entity.Category;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CategoryService {

    /**
     * @Author 阿杰
     * @Description 遍历所有分类
     * @Date 2018/7/20 10:38
     * @Param [page, limit]
     **/
    Page<Category> getCategoryList(int page, int limit);

    /**
     * @Author 阿杰
     * @Description 添加一级分类
     * @Date 2018/7/20 10:51
     * @Param [category, request]
     **/
    ResultUtil addOneCategory(Category category, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 添加二级分类
     * @Date 2018/7/20 10:57
     * @Param [category, request]
     **/
    ResultUtil addTwoCategory(Category category, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 查询一级分类
     * @Date 2018/7/20 11:03
     * @Param []
     **/
    List<Category> findOneCategory();

    List<Category> twoCategory();

    ResultUtil findTwoCategory(String parentName);

    /**
     * @Author 阿杰
     * @Description 删除分类
     * @Date 2018/7/20 15:29
     * @Param [categoryId]
     **/
    ResultUtil deleteCategory(int categoryId);

    /**
     * @Author 阿杰
     * @Description 修改一级二级分类
     * @Date 2018/7/20 16:22
     * @Param [categoryId, request]
     **/
    ResultUtil update(int categoryId, HttpServletRequest request);

    /**
     * @Author 阿杰
     * @Description 后台按分类名搜索分类
     * @Date 2018/7/20 16:31
     * @Param [keyWord, page, limit]
     **/
    Page findCategoryList(String keyWord, int page, int limit);

    //根据id查找分类
    Category findByCategoryId(int categoryId);
}

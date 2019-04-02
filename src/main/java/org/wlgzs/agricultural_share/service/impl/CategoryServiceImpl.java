package org.wlgzs.agricultural_share.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wlgzs.agricultural_share.dao.CategoryRepository;
import org.wlgzs.agricultural_share.entity.Category;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.service.CategoryService;
import org.wlgzs.agricultural_share.util.PageUtil;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author 阿杰
 * @create 2018-07-20 9:47
 * @Description: 分类实现层
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> getCategoryList(int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "categoryId");
        Pageable pageable = PageRequest.of(page, limit, sort);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public ResultUtil addOneCategory(Category category, HttpServletRequest request) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
        if (category.getCategoryName().equals("") || category.getCategoryName() == null) {
            resultUtil.setMsg("分类名不能为空");
            return resultUtil;
        }
        Category category1 = categoryRepository.checkCategoryName(request.getParameter("categoryName"));
        if (category1 != null) {
            resultUtil.setMsg("该分类名已经存在");
            return resultUtil;
        }
        category.setParentId(0);
        category.setParentName("0");
        categoryRepository.save(category);
        return new ResultUtil(ResultEnums.SAVE);
    }

    @Override
    public ResultUtil addTwoCategory(Category category, HttpServletRequest request) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
        if (category.getCategoryName().equals("") || category.getCategoryName() == null) {
            resultUtil.setMsg("分类名不能为空");
            return resultUtil;
        }
        Category category1 = categoryRepository.checkCategoryName(request.getParameter("categoryName"));
        if (category1 != null) {
            resultUtil.setMsg("该分类名已经存在");
            return resultUtil;
        }
        Category category2 = categoryRepository.checkCategoryName(request.getParameter("parentName"));
        category.setParentId(category2.getCategoryId());
        category.setCategoryShow(0);
        categoryRepository.save(category);
        return new ResultUtil(ResultEnums.SAVE);
    }

    @Override
    public List<Category> findOneCategory() {
        List<Category> categories = categoryRepository.findOneCategory();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryName().equals("用户需求")) {
                categories.remove(i);
            }
        }
        return categories;
    }

    @Override
    public List<Category> twoCategory() {
        return categoryRepository.twoCategory();
    }

    @Override
    public ResultUtil findTwoCategory(String parentName) {
        if(parentName.equals("")){
            return new ResultUtil(ResultEnums.UNKONW_ERROR);
        }
        List<Category> categories = categoryRepository.findTwoCategory(parentName);
        return new ResultUtil(ResultEnums.FIND,categories);
    }

    @Override
    public ResultUtil deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            if (category.getParentId() == 0) {
                List<Category> categories = categoryRepository.findByOneCategoryName(category.getCategoryId());
                if (categories.size() != 0) {
                    int[] Ids = new int[categories.size()];
                    for (int i = 0; i < Ids.length; i++) {
                        Ids[i] = categories.get(i).getCategoryId();
                    }
                    categoryRepository.deleteByIds(Ids);
                }
            }
            categoryRepository.deleteById(categoryId);
            return new ResultUtil(ResultEnums.DELETE);
        }
        return new ResultUtil(ResultEnums.UNDELETE);
    }

    @Override
    public ResultUtil update(int categoryId, HttpServletRequest request) {
        ResultUtil resultUtil = new ResultUtil(ResultEnums.UNFIND);
        Map<String, String[]> properties = request.getParameterMap();
        Category category = new Category();
        try {
            BeanUtils.populate(category, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (category.getCategoryName().equals("") || category.getCategoryName() == null) {
            resultUtil.setMsg("分类名不能为空");
            return resultUtil;
        }
        Category category1 = categoryRepository.checkCategoryName(request.getParameter("categoryName"));
        if (category1 != null) {
            resultUtil.setMsg("该分类名已经存在");
            return resultUtil;
        }
        category.setCategoryId(categoryId);
        Category category2 = categoryRepository.checkCategoryName(category.getParentName());
        category.setParentId(category2.getCategoryId());
        category.setCategoryShow(0);
        categoryRepository.saveAndFlush(category);
        return new ResultUtil(ResultEnums.UPDATE);
    }

    @Override
    public Page findCategoryList(String keyWord, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC,"categoryId");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Specification<Category> specification = new PageUtil<Category>(keyWord).getPage("categoryName");
        return categoryRepository.findAll(specification, pageable);
    }

    @Override
    public Category findByCategoryId(int categoryId) {
        return categoryRepository.findById(categoryId);
    }
}

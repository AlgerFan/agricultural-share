package org.wlgzs.agricultural_share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.agricultural_share.entity.Category;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Integer>,JpaSpecificationExecutor<Category> {

    Category findById(int categoryId);
    
    @Query("SELECT c FROM Category c WHERE c.categoryName=?1")
    Category checkCategoryName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.parentId=0")
    List<Category> findOneCategory();
    
    @Query("SELECT c FROM Category c WHERE c.parentId=?1")
    List<Category> findByOneCategoryName(int parentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.categoryId in :Ids")
    void deleteByIds(@Param(value = "Ids") int[] ids);

    @Query("SELECT c FROM Category c WHERE c.parentName=?1")
    List<Category> findTwoCategory(@Param(value = "parentName") String parentName);

    @Query("SELECT c FROM Category c WHERE c.parentId=0")
    List<Category> twoCategory();

    @Query("select c from Category c where c.categoryName=?1")
    Category findByTwoCategory(@Param(value = "sonType") String sonType);
}

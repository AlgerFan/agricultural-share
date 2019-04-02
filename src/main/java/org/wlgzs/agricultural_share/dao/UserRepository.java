package org.wlgzs.agricultural_share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.agricultural_share.entity.User;

import java.util.List;

/**
 * @author 阿杰
 * @create 2018-07-18 11:33
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
    User findById(long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.userId in :Ids")
    void deleteByIds(@Param(value = "Ids") long [] Ids);

    @Query("SELECT u FROM User u WHERE u.userId in :Ids")
    List<User> findByIds(@Param(value = "Ids") long [] Ids);

    @Query("SELECT u FROM User u WHERE u.userName=?1")
    User checkUserName(String userName);

    @Query("SELECT u FROM User u WHERE u.userName=?1")
    List<User> checkUserNames(String userName);

    @Query("SELECT u FROM User u WHERE u.userPhone=?1")
    User checkPhone(String userPhone);
}

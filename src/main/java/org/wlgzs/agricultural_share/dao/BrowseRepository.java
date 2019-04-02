package org.wlgzs.agricultural_share.dao;

import org.springframework.data.jpa.repository.*;
import org.wlgzs.agricultural_share.entity.Browse;

import java.util.List;

public interface BrowseRepository extends JpaRepository<Browse,Integer> ,JpaSpecificationExecutor<Browse> {

    @Query("SELECT b FROM Browse b WHERE b.userId=?1 order by b.messageId desc")
    List<Browse> findUserAllBrowse(long userId);

    @Query("SELECT b FROM Browse b WHERE b.userId = ?1 and b.title like %?2% order by b.messageId desc")
    List<Browse> searchUserBrowse(long userId,String keyWord);

    @Query("select b from Browse b where b.messageId=?1 and b.userId=?2")
    Browse findByMessageIdAndUserId(int messageId, long userId);
}

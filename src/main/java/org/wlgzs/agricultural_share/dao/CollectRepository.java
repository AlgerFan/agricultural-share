package org.wlgzs.agricultural_share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.wlgzs.agricultural_share.entity.Collect;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect,Integer>,JpaSpecificationExecutor<Collect> {

    @Query("select c from Collect c where c.userId=?1 order by c.messageId desc")
    List<Collect> userCollect(long userId);

    @Query("select c from Collect c where c.userId=?1 and c.title like %?2% order by c.messageId desc")
    List<Collect> findUserCollect(long userId,String keyWord);

    @Query("select c from Collect c where c.messageId=?1 and c.userId=?2")
    Collect findByCollectId(int messageId, long userId);

    @Query("select c from Collect c where c.messageId=?1 and c.userId=?2")
    Collect findUserIdAndMessageId(int messageId, long userId);
}

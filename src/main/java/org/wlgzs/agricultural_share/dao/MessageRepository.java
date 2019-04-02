package org.wlgzs.agricultural_share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.agricultural_share.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer>,JpaSpecificationExecutor<Message> {

    @Query("SELECT m FROM Message m WHERE m.messageId in :messageId order by m.messageId desc")
    List<Message> findMessageByIds(@Param("messageId") int[] messageId);

    @Query("SELECT m FROM Message m WHERE m.messageId=?1")
    Message findByMessageId(int messageId);

    @Query("SELECT m FROM Message m WHERE m.messageId=?1 and m.userId=?2")
    Message findByMessageIdAndUserId(int messageId, long userId);

    @Query("select m from Message m where m.sonType=?1 and m.parentType='用户需求'")
    List<Message> findByCategory(String category);

    @Query("select m from Message m where m.parentType='用户需求'")
    List<Message> findDemand();

    @Query(value = "select * from t_message where collections=1 ORDER BY message_id desc limit 10",nativeQuery = true)
    List<Message> findAllIndex();

    @Query("select m from Message m where m.parentType=?1")
    List<Message> searchMessageList(String title);

    @Query("select m from Message m where m.parentType=?1")
    List<Message> homeSearchMassage(String parentType);

    @Query("select m from Message m where m.userId=?1 and m.parentType='用户需求' ORDER BY message_id desc")
    List<Message> recommended(long userId);

}

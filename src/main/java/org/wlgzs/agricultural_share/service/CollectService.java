package org.wlgzs.agricultural_share.service;

import org.springframework.data.domain.Page;
import org.wlgzs.agricultural_share.entity.*;
import org.wlgzs.agricultural_share.enums.ResultEnums;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

public interface CollectService {
    /**
     *  【添加收藏记录】
     *  获取messageId,查找信息详情，创建收藏记录
     * @param messageId  信息Id
     * @param session    获取用户信息
     * @return  添加是否成功
     */
    @Transactional
    ResultEnums addCollect(int messageId, HttpSession session);

    /**【删除收藏记录】
     * 获取主要包括信息Id和用户的信息，进行比较，确认作者或管理员可删除
     * @param collectId 记录Id
     * @param session  获取用户信息
     * @return
     */
    ResultEnums deleteCollect(int collectId, HttpSession session);

    /**
     * @Description 足迹移至收藏
     * @Param [messageId, browseId, userId]
     **/
    void removeCollect(int messageId, int browseId, long userId);


    /**
     * 【批量删除】
     * 获得Id数组，先对比是否为作者或管理员，完全满足进行删除，不完全满足返回操作失败
     * @param collectIds  id数组
     * @param session     获取用户信息
     * @return      删除是否成功
     */
    ResultEnums deleteCollect(int[] collectIds, HttpSession session);

    /**
     * 【分页检索用户收藏记录】
     * @param request  获取用户信息
     * @return  收藏记录的分页信息
     */
    Page<Collect> seeCollectByPage(HttpServletRequest request);
    /**
     * @Description 遍历用户收藏
     * @Date 2018/7/31 8:27
     * @Param [model, request]
     **/
    List<Collect> userCollect(long userId);
    /**
     * @Description 查找用户收藏
     * @Date 2018/7/31 8:32
     * @Param [model, request]
     **/
    List<Collect> findUserCollect(long userId,String keyWord);

    Collect findUserIdAndMessageId(int messageId, long userId);
}

package org.wlgzs.agricultural_share.service;

import org.springframework.data.domain.Page;
import org.wlgzs.agricultural_share.entity.*;
import org.wlgzs.agricultural_share.enums.ResultEnums;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface BrowseService {

    /**
     * 用户阅读发布信息
     * @param messageId
     * @param request
     * @return  包含message和图片地址的map
     */
    boolean read(int messageId, HttpServletRequest request);

    /**
     * @Description 查找用户所有足迹
     * @Date 2018/7/30 20:31
     * @Param [model, request]
     **/
    List<Browse> findUserAllBrowse(long userId);

    /**
     * 【删除浏览记录】获取主要包括信息Id和用户的信息，进行比较，确认作者或管理员可删除
     * @param browseId 记录Id
     * @param session 获取用户信息
     * @return
     */
    ResultEnums deleteBrowse(int browseId, HttpSession session);

    /**
     * @Description 搜索用户足迹
     * @Param [keyWord, page, limit]
     **/
    List<Browse> searchUserBrowse(long userId,String keyWord);

    /**
     * 【批量删除】
     * 获得Id数组，先对比是否为作者或管理员，完全满足进行删除，不完全满足返回操作失败
     * @param browseIds  id数组
     * @param session  获取用户信息
     * @return  删除是否成功
     */
    ResultEnums deleteBrowse(int[] browseIds, HttpSession session);

    /**
     * 【分页检索用户记录】
     * @param request  获取用户信息
     * @return  记录的分页信息
     */
    Page<Browse> seeBrowseByPage(HttpServletRequest request);

    /**
     * 【获取用户浏览记录】：默认不多于100条，供用户推荐使用
     * @param userId   用户Id
     * @return    用户浏览记录的分页信息
     */
    List<Browse> toUserBrowse(long userId);

}

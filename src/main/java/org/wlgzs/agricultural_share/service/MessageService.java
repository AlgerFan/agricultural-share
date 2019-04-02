package org.wlgzs.agricultural_share.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.agricultural_share.entity.Message;
import org.wlgzs.agricultural_share.enums.ResultEnums;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public interface MessageService {
    /**
     * 【发布信息】
     * 获取请求中的message对象的数据判断后写入数据库
     *
     * @param request 获取用户信息
     * @param message 请求中的message对象
     * @return
     */
    boolean addMessage(HttpServletRequest request, Message message, List<MultipartFile> files);

    /**
     * 【单条删除】
     * 获取主要包括信息Id和用户的信息，进行比较，确认作者或管理员可删除
     * @param request 获取请求中的数据
     * @return 操作是否成功
     */
    ResultEnums deleteMessage(HttpServletRequest request);

    /**
     * 【高级检索】
     * 获得参数进行多条件查询，默认点击量排行，
     * 不指定信息则以首页十条记录为标准进行查找，
     * 获得参数为空默认不过滤此条件
     *
     * @param request 可获得参数【页码，内容数量，关键词，是否最新发布，是否收藏排序，一级模块，二级分类】
     * @return
     */
    Page<Message> serchMessage(HttpServletRequest request);

    /**
     * 【批量删除】
     * 获得Id数组，先对比是否为作者或管理员，完全满足进行删除，不完全满足返回操作失败
     *
     * @param messageIds id数组
     * @param session    获取用户信息
     * @return  删除是否成功
     */
    ResultEnums deleteMessage(int[] messageIds, HttpSession session);

    /**
     * @Description 通过ids查找发布信息
     * @Date 2018/7/31 8:55
     * @Param [messageId]
     **/
    List<Message> findMessageByIds(int[] messageId);
    /**
     * @Description 遍历所有发布信息
     * @Date 2018/7/31 8:56
     * @Param [page, limit]
     **/
    Page findAllMessage(int page, int limit);
    /**
     * @Description 搜索发布信息
     * @Date 2018/7/31 8:57
     * @Param [keyWord, page, limit]
     **/
    Page findMessages(String keyWord, int page, int limit);
    /**
     * @Description 需求发布
     * @Date 2018/7/31 21:11
     * @Param [message, request, response]
     **/
    void createDemand(HttpServletRequest request, Message message, long userId);
    /**
     * @Description 根据分类查找需求
     * @Date 2018/7/31 18:04
     * @Param [category]
     **/
    List<Message> findDemandByCategory(String category);

    /**
     * 查找所有需求
     */
    List<Message> findDemand();

    List<Message> index();

    /**
     * @Description 遍历所有发布信息
     * @Date 2018/7/31 21:12
     * @Param [model]
     **/
    List<Message> allMessageList();
    /**
     * @Description 搜索发布信息
     * @Date 2018/7/31 21:12
     * @Param [model]
     **/
    List<Message> searchMessageList(String title);
    /**
     * @Description 主页按照一级分类搜索发布信息
     * @Date 2018/7/31 21:20
     * @Param [model, parentType]
     **/
    List<Message> homeSearchMassage(String parentType);

    /**
     * 推荐农资
     * @param request
     * @param model
     */
    List<Message> recommended(HttpServletRequest request);
}

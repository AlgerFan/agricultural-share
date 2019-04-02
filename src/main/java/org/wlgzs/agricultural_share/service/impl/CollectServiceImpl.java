package org.wlgzs.agricultural_share.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Collect;
import org.wlgzs.agricultural_share.entity.Message;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.service.CollectService;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl extends BaseController implements CollectService {
    final int adminRole = 1;

    @Override
    public ResultEnums addCollect(int messageId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        /*User user = new User();
        user.setUserId(35);*/
        Message message = messageResponsitory.getOne(messageId);
        if (user == null || message == null) return ResultEnums.UNSAVE;
        Collect collect1 = collectResponsitory.findByCollectId(messageId,user.getUserId());
        if (collect1!=null) {
            collectResponsitory.deleteById(collect1.getCollectId());
        }
        Collect collect = new Collect(0, user.getUserId(), messageId, message.getTitle(), new Date());
        collectResponsitory.save(collect);
        return ResultEnums.SAVE;
    }
    @Override
    public ResultEnums deleteCollect(int collectId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Collect collect = collectResponsitory.getOne(collectId);
        if (collect == null || user == null || collect.getUserId() != user.getUserId())
            return ResultEnums.UNDELETE;
        collectResponsitory.delete(collect);
        return ResultEnums.DELETE;
    }

    @Override
    public void removeCollect(int messageId, int browseId, long userId) {
        browseResponsitory.deleteById(browseId);
        Message message = messageResponsitory.findByMessageId(messageId);
        Collect collect1 = collectResponsitory.findByCollectId(messageId,userId);
        if (collect1!=null) {
            collectResponsitory.deleteById(collect1.getCollectId());
        }
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setMessageId(messageId);
        collect.setTitle(message.getTitle());
        Date date = new Date();
        collect.setBuildDate(date);
        collectResponsitory.save(collect);
    }


    @Override
    public ResultEnums deleteCollect(int[] collectIds, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (collectIds.length == 0 || user == null) return ResultEnums.UNDELETE;
        for (int collectId : collectIds) {
            System.out.println();
            if (user.getUserRole() == adminRole) break;
            if (collectResponsitory.getOne(collectId).getUserId() != user.getUserId()) return ResultEnums.UNDELETE;
        }
        for (int collectId : collectIds) collectResponsitory.deleteById(collectId);
        return ResultEnums.DELETE;
    }

    @Override
    public Page<Collect> seeCollectByPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String keyWord = request.getParameter("keyWord");
        String pageNoStr = request.getParameter("pageNo"), pageSizeStr = request.getParameter("pageSize");
        int pageNo, pageSize;
        if (pageNoStr == null) pageNo = 1;
        else pageNo = Integer.parseInt(pageNoStr);
        if (pageSizeStr == null) pageSize = 10;
        else pageSize = Integer.parseInt(pageSizeStr);
        User user = (User) session.getAttribute("user");
        if (user == null) return null;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "buildDate");
        Specification<Collect> specification = new Specification<Collect>() {
            @Override
            public Predicate toPredicate(Root<Collect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path title = root.get("title");
                Path userId = root.get("userId");
                System.out.println();
                if (keyWord != null) predicates.add(criteriaBuilder.equal(title, title));
                predicates.add(criteriaBuilder.equal(userId, user.getUserId()));
                Predicate[] predicatesnew = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicatesnew));
            }
        };
        return collectResponsitory.findAll(specification, pageable);
    }

    @Override
    public List<Collect> userCollect(long userId) {
        return collectResponsitory.userCollect(userId);
    }

    @Override
    public List<Collect> findUserCollect(long userId,String keyWord) {
        return collectResponsitory.findUserCollect(userId,keyWord);
    }

    @Override
    public Collect findUserIdAndMessageId(int messageId, long userId) {
        return collectResponsitory.findUserIdAndMessageId(messageId,userId);
    }

}

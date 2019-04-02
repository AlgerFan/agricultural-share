package org.wlgzs.agricultural_share.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.Browse;
import org.wlgzs.agricultural_share.entity.Message;
import org.wlgzs.agricultural_share.entity.User;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.service.BrowseService;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Service
public class BrowseServiceImpl extends BaseController implements BrowseService {

    @Override
    public boolean read(int messageId, HttpServletRequest request) {
        Message message = messageResponsitory.getOne(messageId);
        if (!message.getParentType().equals(needType)) {
            List<String> pictureUrls = new ArrayList<>();
            File[] files = (new File("." + RootUrl + messageId)).listFiles();
            if (files == null || files.length == 0 || message == null) return false;
            for (File file : files) pictureUrls.add(RootUrl + file.getName());
            request.setAttribute("pictures", pictureUrls);
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Browse browse = browseResponsitory.findByMessageIdAndUserId(messageId,user.getUserId());
            if (browse!=null) {
                browseResponsitory.deleteById(browse.getBrowseId());
            }
            browseResponsitory.save(new Browse(0, user.getUserId(), messageId, message.getTitle(), new Date()));
        }
        request.setAttribute("message", message);
        return true;
    }

    @Override
    public List<Browse> findUserAllBrowse(long userId) {
        return browseResponsitory.findUserAllBrowse(userId);
    }

    @Override
    public ResultEnums deleteBrowse(int browseId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Browse browse = browseResponsitory.getOne(browseId);
        if (browse == null || user == null || browse.getUserId() != user.getUserId())
            return ResultEnums.UNDELETE;
        browseResponsitory.delete(browse);
        return ResultEnums.DELETE;
    }

    @Override
    public List<Browse> searchUserBrowse(long userId,String keyWord) {
        return browseResponsitory.searchUserBrowse(userId,keyWord);
    }

    @Override
    public ResultEnums deleteBrowse(int[] browseIds, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (browseIds.length == 0 || user == null) return ResultEnums.UNDELETE;
        for (int browseId : browseIds) {
            if (user.getUserRole() == adminRole) break;
            if (browseResponsitory.getOne(browseId).getUserId() != user.getUserId()) return ResultEnums.UNDELETE;
        }
        for (int browseId : browseIds) browseResponsitory.deleteById(browseId);
        return ResultEnums.DELETE;
    }

    @Override
    public Page<Browse> seeBrowseByPage(HttpServletRequest request) {
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
        return getPage(pageNo, pageSize, keyWord, user);
    }

    @Override
    public List<Browse> toUserBrowse(long userId) {
        User user = userRepository.getOne(userId);
        if (user == null) return null;
        return getPage(1, 100, null, user).getContent();
    }


    public Page<Browse> getPage(int pageNo, int pageSize, String keyWord, User user) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "buildDate");
        Specification<Browse> specification = new Specification<Browse>() {
            @Override
            public Predicate toPredicate(Root<Browse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path title = root.get("title");
                Path userId = root.get("userId");
                if (keyWord != null) predicates.add(criteriaBuilder.equal(title, "%" + keyWord + "%"));
                predicates.add(criteriaBuilder.equal(userId, user.getUserId()));
                Predicate[] predicatesnew = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicatesnew));
            }
        };
        return browseResponsitory.findAll(specification, pageable);
    }

}

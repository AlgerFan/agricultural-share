package org.wlgzs.agricultural_share.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.entity.*;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.service.MessageService;
import org.wlgzs.agricultural_share.util.CheckImage;
import org.wlgzs.agricultural_share.util.PageUtil;
import org.wlgzs.agricultural_share.util.UploadUtil;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class MessageServiceImpl extends BaseController implements MessageService {

    private UploadUtil uploadUtil = new UploadUtil();

    @Override
    public boolean addMessage(HttpServletRequest request, Message message, List<MultipartFile> files) {
        User user = (User) request.getSession().getAttribute("user");
        final int wordsMax = 500, titleMax = 50;
        log.info("农资发布："+message);
        if (user == null || message.getPhone() == null || message.getPhone().equals("") ||
                message.getAddress() == null || message.getAddress().equals("") ||
                message.getSonType() ==null || message.getSonType().equals("") ||
                message.getTitle() == null || message.getTitle().equals("") || message.getTitle().length() > titleMax ||
                message.getContent() == null || message.getContent().equals("") || message.getContent().length() > wordsMax)
            return false;
        Category category = categoryRepository.findByTwoCategory(message.getSonType());
        message.setParentType(category.getParentName());
        message.setUserId(user.getUserId());
        message.setBuildDate(new Date());
        message.setCollections(1);
        Message saveMessage = messageResponsitory.save(message);
        log.info("农资发布："+saveMessage);
        return !message.getParentType().equals(needType) && savePicture(files, saveMessage);
    }

    @Override
    public ResultEnums deleteMessage(HttpServletRequest request) {
        //User user = (User) session.getAttribute("user");
        Message message = messageResponsitory.getOne(Integer.parseInt(request.getParameter("messageId")));
        /*if (message == null || user == null || message.getUserId() != user.getUserId() && user.getUserRole() != 0)
            return ResultEnums.UNDELETE;*/
        uploadUtil.deleteDir(new File("." + RootUrl + message.getMessageId()));
        messageResponsitory.delete(message);
        return ResultEnums.DELETE;
    }

    @Override
    public Page<Message> serchMessage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String keyWord = request.getParameter("keyWord");
        String pageStr = request.getParameter("page"), pageSizeStr = request.getParameter("pageSize");
        int pageNo, pageSize;
        if (pageStr == null) pageNo = 1;
        else pageNo = Integer.parseInt(pageStr);
        if (pageSizeStr == null) pageSize = 10;
        else pageSize = Integer.parseInt(pageSizeStr);
        String searchParentType = request.getParameter("parentType"), searchSonType = request.getParameter("sonType"),
                searchAddress = request.getParameter("address");
        String isMine;
        if (request.getAttribute("isMine")!=null)isMine = request.getAttribute("isMine").toString();else isMine="false";
        Sort sort = new Sort(Sort.Direction.DESC, getSorts(request.getParameter("collect"), request.getParameter("click")));
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Specification<Message> messageSpecification = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<String> parentType = root.get("parentType");
                Path<String> sonType = root.get("sonType");
                Path<String> address = root.get("address");
                Path<String> title = root.get("title");
                Path userId = root.get("userId");
                if (searchParentType != null && searchSonType == null)
                    predicates.add(criteriaBuilder.equal(parentType, searchParentType));
                else if (searchParentType == null && searchSonType != null)
                    predicates.add(criteriaBuilder.equal(sonType, searchSonType));
                if (searchAddress != null) predicates.add(criteriaBuilder.equal(address, searchAddress + "%"));
                if (keyWord != null) predicates.add(criteriaBuilder.equal(title, "%" + keyWord + "%"));
                if (isMine != null && isMine.equals("true")) predicates.add(criteriaBuilder.equal(userId, user.getUserId()));
                Predicate[] predicatesnew = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicatesnew));
            }
        };
        return messageResponsitory.findAll(messageSpecification, pageable);
    }

    @Override
    public ResultEnums deleteMessage(int[] messageIds, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || messageIds.length == 0) return ResultEnums.UNDELETE;
        for (int messageId : messageIds) {
            if (user.getUserRole() == adminRole) break;
            if (messageResponsitory.getOne(messageId).getUserId() != user.getUserId()) return ResultEnums.UNDELETE;
        }
        for (int messageId : messageIds) {
            uploadUtil.deleteDir(new File("." + RootUrl + messageId));
            messageResponsitory.deleteById(messageId);
        }
        return ResultEnums.DELETE;
    }

    @Override
    public List<Message> findMessageByIds(int[] messageId) {
        return messageResponsitory.findMessageByIds(messageId);
    }

    @Override
    public Page findAllMessage(int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "messageId");
        Pageable pageable = PageRequest.of(page, limit, sort);
        return messageResponsitory.findAll(pageable);
    }

    @Override
    public Page findMessages(String keyWord, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "messageId");
        Pageable pageable = PageRequest.of(page, limit, sort);
        Specification<Message> specification = new PageUtil<Message>(keyWord).getPage("title");
        return messageResponsitory.findAll(specification, pageable);
    }

    @Override
    public boolean createDemand(HttpServletRequest request, Message message) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null || message.getSonType()==null || message.getSonType().equals("") ||
                message.getPhone()==null || message.getPhone().equals("") ||
                message.getTitle()==null || message.getTitle().equals("") ||
                message.getContent()==null || message.getContent().equals("") ||
                message.getAddress()==null || message.getAddress().equals("")) {
            return false;
        }
        message.setUserId(user.getUserId());
        message.setBuildDate(new Date());
        log.info("新增需求："+message);
        messageResponsitory.save(message);
        //修改推荐状态
        User user1 = userRepository.findById(user.getUserId());
        user1.setRecommended(1);
        userRepository.saveAndFlush(user1);
        session.setMaxInactiveInterval(60 * 20);
        session.setAttribute("user", user1);
        return true;
    }

    @Override
    public List<Message> findDemandByCategory(String category) {
        return messageResponsitory.findByCategory(category);
    }

    @Override
    public List<Message> findDemand() {
        return messageResponsitory.findDemand();
    }

    @Override
    public List<Message> index() {
        return messageResponsitory.findAllIndex();
    }

    @Override
    public List<Message> allMessageList() {
        return messageResponsitory.findAll();
    }

    @Override
    public List<Message> searchMessageList(String title) {
        return messageResponsitory.searchMessageList(title);
    }

    @Override
    public List<Message> homeSearchMassage(String parentType) {
        return messageResponsitory.homeSearchMassage(parentType);
    }

    @Override
    public List<Message> recommended(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user.setRecommended(0);
        userRepository.saveAndFlush(user);
        session.setMaxInactiveInterval(60 * 20);
        session.setAttribute("user", user);
        List<Message> messages = messageResponsitory.recommended(user.getUserId());
        log.info("最新发布需求："+messages);
        List<Message> messages1;
        if(messages!=null) {
            Category byTwoCategory = categoryRepository.findByTwoCategory(messages.get(0).getSonType());
            log.info("推荐分类："+byTwoCategory);
            messages1 = messageResponsitory.searchMessageList(byTwoCategory.getParentName());
            log.info("推荐农资："+messages1);
        } else {
            messages1 = messageResponsitory.findAllIndex();
        }
        return messages1;
    }

    List<String> getSorts(String collectstr, String neweststr) {
        List<String> sorts = new ArrayList<>();
        sorts.add("buildDate");
        sorts.add("clicks");
        if (collectstr != null) {
            boolean collect = Boolean.getBoolean(collectstr);
            if (collect) sorts.add("collections");
        }
        if (neweststr != null) {
            boolean newest = Boolean.getBoolean(neweststr);
            if (newest) sorts.add("clicks");
        }
        return sorts;
    }

    boolean savePicture(List<MultipartFile> pictures, Message message) {
        final String pathUrl = RootUrl + message.getMessageId() + "/";
        for (MultipartFile picture : pictures) {
            if (!CheckImage.verifyImage(picture.getOriginalFilename())) return false;
        }
        String mainPictureUrl = null;
        int num = 0;
        for (MultipartFile picture : pictures) {
            String filename = picture.getOriginalFilename();
            if (mainPictureUrl == null) {
                mainPictureUrl = pathUrl + "main." + (filename.substring(filename.lastIndexOf(".") + 1));
                System.out.println(mainPictureUrl);
                uploadUtil.saveFile(picture, mainPictureUrl);
            }
            else{ uploadUtil.saveFile(picture, pathUrl + message.getMessageId() + "_" + num+"." + filename.substring(filename.lastIndexOf(".") + 1));
            num++;
            }
        }
        System.out.println(mainPictureUrl+"------------------");
        message.setMainPictureUrl(mainPictureUrl);
        messageResponsitory.save(message);
        return true;
    }
}

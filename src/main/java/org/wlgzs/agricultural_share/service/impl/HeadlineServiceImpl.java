package org.wlgzs.agricultural_share.service.impl;

import org.springframework.stereotype.Service;
import org.wlgzs.agricultural_share.base.BaseController;
import org.wlgzs.agricultural_share.dao.HeadlineRepository;
import org.wlgzs.agricultural_share.entity.Headline;
import org.wlgzs.agricultural_share.enums.ResultEnums;
import org.wlgzs.agricultural_share.service.HeadlineService;
import org.wlgzs.agricultural_share.util.ResultUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HeadlineServiceImpl extends BaseController implements HeadlineService {

    @Resource
    private HeadlineRepository headlineRepository;

    @Override
    public ResultUtil addHeadline(Headline headline) {
        if(headline.getType() == null || headline.getContent() == null) return new ResultUtil(ResultEnums.UNSAVE);
        headlineRepository.save(headline);
        return new ResultUtil(ResultEnums.SAVE);
    }

    @Override
    public ResultUtil deleteById(int headlineId) {
        if(headlineId == 0) return new ResultUtil(ResultEnums.UNDELETE);
        headlineRepository.deleteById(headlineId);
        return new ResultUtil(ResultEnums.DELETE);
    }

    @Override
    public List<Headline> findAll() {
        return headlineRepository.findAll();
    }
}

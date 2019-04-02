package org.wlgzs.agricultural_share.service;

import org.wlgzs.agricultural_share.entity.Headline;
import org.wlgzs.agricultural_share.util.ResultUtil;

import java.util.List;

public interface HeadlineService {

    ResultUtil addHeadline(Headline headline);

    ResultUtil deleteById(int headlineId);

    List<Headline> findAll();
}

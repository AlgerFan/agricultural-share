package org.wlgzs.agricultural_share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.wlgzs.agricultural_share.entity.Headline;

public interface HeadlineRepository extends JpaRepository<Headline,Integer>, JpaSpecificationExecutor<Headline> {
}

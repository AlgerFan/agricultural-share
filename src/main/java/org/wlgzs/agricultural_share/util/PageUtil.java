package org.wlgzs.agricultural_share.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class PageUtil<T> {

    private String searchKeywords;//模糊搜索关键字

    public PageUtil(String searchKeywords) {
        this.searchKeywords=searchKeywords;
    }

    public Specification<T> getPage(String...strings){
        return  new Specification<T>() {
            /**
            * root就是我们要查询的类型
            * query添加查询条件
            * criteriaBuilder构建Predicate
            * */
            @Override
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(searchKeywords==null){ //不模糊查询直接返回
                    return null;
                }
                if(searchKeywords.equals("")){ //不模糊查询直接返回
                    return null;
                }
                List<Predicate> predicates = new ArrayList<Predicate>();
                for (String s:strings){
                    Predicate _name = null;
                    Path<String> $name = root.get(s);
                    _name = criteriaBuilder.like($name, "%" + searchKeywords + "%");
                    predicates.add(_name);
                }
                return criteriaBuilder.or(predicates
                        .toArray(new Predicate[] {}));
            }
        };
    }

}

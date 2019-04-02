package org.wlgzs.agricultural_share.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author 阿杰
 * @create 2018-07-20 9:26
 * @Description:
 */
@Entity
@Data
@Table(name = "t_category")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;//发布信息类别id
    @Column(nullable = false,length = 10)
    private String categoryName;  //类别名称  2      1
    @Column(nullable = false)
    private int parentId; //父分类id
    @Column(nullable = false,length = 10)
    private String parentName; //父分类名字   2
    @Column(nullable = false,length = 10)
    private String unitPrice; //价格单位    2    1
    @Column(nullable = false)
    private int categoryShow; //主页是否显示该分类   1
}

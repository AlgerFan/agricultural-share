package org.wlgzs.agricultural_share.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "t_message")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;      //主键；Id
    @Column(nullable = false)
    private long userId;          //用户Id
    @Column(nullable = false,length = 50)
    private String title;        //标题
    @Column(nullable = false,length = 500)
    private String content;      //描述
    private String parentType;   //模块类型
    @Column(nullable = false)
    private String sonType;      //子类型
    private String mainPictureUrl;//首图地址
    @Column(nullable = false,length = 200)
    private String address;      //地址
    private String phone;        //联系电话
    @Column(nullable = false)
    private Date buildDate;      //发布日期
    private String price;        //价格
    private int count;           //数量及库存
    @Column(columnDefinition="Integer default 1")
    private int clicks;          //点击量
    @Column(columnDefinition="Integer default 1")
    private int collections;     //收藏量
    @Column(nullable = false,columnDefinition = "Boolean default true")
    private boolean isCheck;     //是否审核
}

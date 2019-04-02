package org.wlgzs.agricultural_share.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "t_collect")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Collect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int collectId;       //主键；Id
    @Column(nullable = false)
    private long userId;          //用户Id
    @Column(nullable = false)
    private int messageId;       //信息Id
    @Column(nullable = false)
    private String title;        //信息标题
    private Date buildDate;    //创建时间
}

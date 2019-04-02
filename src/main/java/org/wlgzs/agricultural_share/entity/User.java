package org.wlgzs.agricultural_share.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author 阿杰
 * @create 2018-07-18 11:33
 * @Description: 用户表
 */
@Entity
@Data
@Table(name = "t_user")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;//用户id
    @Column(length = 100)
    private String userAvatar;//用户头像
    @Column(nullable = false,length = 30)
    private String userName;//用户名
    @Column(nullable = false,length = 30)
    private String userPassword;//密码
    @Column(nullable = false,length = 11)
    private String userPhone;//电话
    @Column(length = 30)
    private String userAddress;//地址
    private Integer userRole=0;//角色
    private Integer recommended=0;//推荐
}

package org.wlgzs.agricultural_share.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_headline")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Headline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int headlineId;
    @Column(length = 50)
    private String type;
    @Column(length = 500)
    private String content;
}

package org.wlgzs.agricultural_share.enums;

import lombok.*;

/**
 * @author 阿杰
 * @create 2018-07-18 18:03
 * @Description:
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public enum ResultEnums {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(1, "成功"),
    SAVE(1,"添加成功"),
    UNSAVE(-1,"添加失败"),
    UPDATE(1,"修改成功"),
    UNUPDATE(1,"修改失败"),
    FIND(1,"查询成功"),
    UNFIND(-1,"查询失败"),
    DELETE(1,"删除成功"),
    UNDELETE(-1,"删除失败"),
    ;

    @Getter
    @Setter
    private Integer code;//状态码

    @Getter
    @Setter
    private String msg;//状态信息
}

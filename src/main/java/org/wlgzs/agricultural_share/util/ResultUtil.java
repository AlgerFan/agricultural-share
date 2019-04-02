package org.wlgzs.agricultural_share.util;

import lombok.Data;
import org.wlgzs.agricultural_share.enums.ResultEnums;

/**
 * @author 阿杰
 * @create 2018-07-18 18:03
 * @Description:
 */
@Data
public class ResultUtil {

    //状态码
    private int code;
    //状态信息
    private String msg;
    //返回数据
    private Object data;

    public ResultUtil(ResultEnums resultEnums) {
        this.code = resultEnums.getCode();
        this.msg = resultEnums.getMsg();
    }

    public ResultUtil(ResultEnums resultEnums, Object data) {
        this(resultEnums);
        this.data = data;
    }
}

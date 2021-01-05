package com.shengsheng.wx.dto;

import java.io.Serializable;

/**
 * description:
 *
 * @author shengsheng
 * @date 2021/1/5 17:14
 */
public class DecryptCode extends BaseResponse implements Serializable {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

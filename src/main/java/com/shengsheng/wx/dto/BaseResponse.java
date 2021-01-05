package com.shengsheng.wx.dto;

import java.io.Serializable;

/**
 * description:
 *
 * @author xuqiangsheng
 * @date 2021/1/5 17:12
 */
public class BaseResponse implements Serializable {
    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}

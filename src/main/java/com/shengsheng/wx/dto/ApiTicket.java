package com.shengsheng.wx.dto;

/**
 * @author xuqiangsheng
 */
public class ApiTicket extends BaseResponse {
    private String ticket;
    private Long expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        //原expiresIn是有效时长，比如：7200，现改为过期的时间戳
        this.expiresIn = System.currentTimeMillis() + (expiresIn - 100) * 1000;
    }
}

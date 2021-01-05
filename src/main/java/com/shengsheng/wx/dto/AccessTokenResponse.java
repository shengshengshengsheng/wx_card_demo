package com.shengsheng.wx.dto;

/**
 * description:
 *
 * @author shengsheng
 * @date 2021/1/5 20:26
 */
public class AccessTokenResponse extends BaseResponse{
    private String accessToken;
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = System.currentTimeMillis() + (expiresIn - 100) * 1000;
    }
}

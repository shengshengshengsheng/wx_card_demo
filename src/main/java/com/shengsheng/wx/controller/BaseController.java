package com.shengsheng.wx.controller;

/**
 * description:
 *
 * @author shengsheng
 * @date 2021/1/5 21:13
 */
public class BaseController {
    public static class Path {
        //微信-获取accessToken
        public static final String GET_WX_ACCESS_TOKEN = "/weixin/getAccessToken";

        //微信-卡券-小程序端打通-小程序内领取卡券-获取签名
        public static final String GET_WX_CARD_SIGN = "/weixin/getCardSign";
    }
}

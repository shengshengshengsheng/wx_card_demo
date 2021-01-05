package com.shengsheng.wx.controller;

import com.shengsheng.wx.service.WeiXinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author shengsheng
 * @date 2021/1/5 20:05
 */
@RestController
public class WxController extends BaseController{
    @Autowired
    private WeiXinService weiXinService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 卡券-小程序端打通-小程序内领取卡券-获取签名
     * @param cardId 优惠券id(微信提供)
     * @return
     */
    @PostMapping(value = Path.GET_WX_CARD_SIGN)
    public Map<String, String> getCardSign(@RequestParam("card_id") String cardId){
        try {
            return weiXinService.getCardSign(cardId);
        } catch (Exception e) {
            logger.error("WxController_getCardSign_error", e);
            return new HashMap<>(1);
        }
    }

    /**
     * 微信-获取accessToken
     * @return
     */
    @PostMapping(value = Path.GET_WX_ACCESS_TOKEN)
    public String getAccessToken(){
        try {
            return weiXinService.getAccessToken();
        } catch (Exception e) {
            logger.error("WxController_getAccessToken_error", e);
            return "";
        }
    }
}

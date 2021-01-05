package com.shengsheng.wx.controller;

import com.shengsheng.wx.service.WeiXinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author xuqiangsheng
 * @date 2021/1/5 20:05
 */
@RestController
@RequestMapping("/weixin")
public class WxController {
    @Autowired
    private WeiXinService weiXinService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 小程序端wx.addCard获取签名
     * @param cardId 优惠券id(微信提供)
     * @return
     */
    @RequestMapping(value = "/getCardSign")
    public Map<String, String> getCardSign(@RequestParam("card_id") String cardId){
        try {
            return weiXinService.getCardSign(cardId);
        } catch (Exception e) {
            logger.error("WxController_getCardSign_error", e);
            return new HashMap<>(1);
        }
    }
}

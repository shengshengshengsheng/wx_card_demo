package com.shengsheng.wx.controller;

import com.shengsheng.wx.service.WeiXinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 微信公众号消息回调 get方法用于微信鉴权
     * @param signature 微信的签名，需要与自己生成的签名进行比对，相同则成功
     * @param timestamp 时间戳
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = Path.WEIXIN_CALLBACK, method = RequestMethod.GET)
    public String cardCouponCallback(@RequestParam(name = "signature",required = false) String signature,
                                     @RequestParam(name = "timestamp",required = false) String timestamp,
                                     @RequestParam(name = "nonce",required = false) String nonce,
                                     @RequestParam(name = "echostr",required = false) String echostr) {
        try {
            logger.info("-----签名开始-----");
            boolean flag = weiXinService.verifyUrl(signature, timestamp, nonce, echostr);
            if (flag) {
                logger.info("cardCouponCallbackCheckPass");
                return echostr;
            }
            logger.info("cardCouponCallbackCheckFailure");
            return "error";
        } catch (Exception e) {
            logger.info("cardCouponCallbackCheckFailure", e);
            return "error";
        }
    }

    /**
     * 处理具体的回调信息
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param openid
     * @param msg
     * @return
     */
    @RequestMapping(value = Path.WEIXIN_CALLBACK, method = RequestMethod.POST)
    public String handleWxCallBackMsg(@RequestParam(name = "signature",required = false) String signature,
                                      @RequestParam(name = "timestamp",required = false) String timestamp,
                                      @RequestParam(name = "nonce",required = false) String nonce,
                                      @RequestParam(name = "echostr",required = false) String echostr,
                                      @RequestParam(name = "openid",required = false) String openid,
                                      @RequestBody String msg) {
        try {
            weiXinService.handleWxCallBackMsg(msg,signature,timestamp,nonce,echostr,openid);
            return "success";
        } catch (Exception e) {
            logger.info("handleWxCallBackMsg", e);
            return "error";
        }
    }
}

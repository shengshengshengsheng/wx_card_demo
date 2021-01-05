package com.shengsheng.wx.service;

import com.alibaba.fastjson.JSON;
import com.shengsheng.wx.dto.AccessTokenResponse;
import com.shengsheng.wx.dto.ApiTicket;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * description:
 *
 * @author shengsheng
 * @date 2021/1/5 20:07
 */
@Service
public class WeiXinService {

    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Map<String, String> getCardSign(String cardId) {
        //获取ticket
        String apiTicket = getTicketForCard();
        if (StringUtils.isBlank(apiTicket)) {
            logger.info("getCardSign_error:获取到的ticket为空");
            return new HashMap<>(1);
        }
        //生成领取卡券需要的签名，并返回相关的参数
        Map<String, String> ret = new HashMap<>(6);
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String signature;
        String[] param = new String[4];
        param[0] = nonceStr;
        param[1] = timestamp;
        param[2] = apiTicket;
        param[3] = cardId;
        //对参数的value值进行字符串的字典序排序
        Arrays.sort(param);
        StringBuilder sb = new StringBuilder();
        Arrays.stream(param).forEach(sb::append);
        //对上面拼接的字符串进行sha1加密，得到signature
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sb.toString().getBytes(StandardCharsets.UTF_8));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.info("getCardSign_error:", e);
            return ret;
        }
        //返回领取卡券需要的参数，其中nonceStr和timestamp必须和签名中的保持一致
        ret.put("card_id", cardId);
        ret.put("apiTicket", apiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private String getTicketForCard() {
        //从redis获取ticket
        String key = "wxTicketForWxCard_";
        String ticket;
        ApiTicket response = JSON.parseObject(JSON.toJSONString(stringRedisTemplate.opsForValue().get(key)),
                ApiTicket.class);
        //token失效或者不存在
        if (response == null || response.getExpiresIn().compareTo(System.currentTimeMillis()) <= 0 || StringUtils.isBlank(response.getTicket())) {
            //从微信端获取ticket
            ticket = getTicketFromWx();
        } else {
            ticket = response.getTicket();
        }
        return ticket;
    }

    private String getTicketFromWx() {
        String accessToken = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApiTicket> forEntity = restTemplate.getForEntity("https://api.weixin.qq" +
                        ".com/cgi-bin/ticket/getticket?type=wx_card&access_token=" + accessToken,
                ApiTicket.class);
        logger.info("getTicketFromWx_ApiTicket:{}", JSON.toJSONString(forEntity));
        ApiTicket apiTicket = forEntity.getBody();
        if (apiTicket == null || StringUtils.isBlank(apiTicket.getTicket())) {
            logger.info("getTicketFromWx_error:获取ApiTicket出错");
            return null;
        }
        String key = "wxTicketForWxCard_";
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(apiTicket));
        return apiTicket.getTicket();
    }

    private String getAccessToken() {
        //从redis获取accessToken
        String key = "wxAccessToken_";
        String accessToken;
        AccessTokenResponse response = JSON.parseObject(JSON.toJSONString(stringRedisTemplate.opsForValue().get(key))
                , AccessTokenResponse.class);
        //token失效或者不存在
        if (response == null || response.getExpiresIn().compareTo(System.currentTimeMillis()) <= 0 || StringUtils.isBlank(response.getAccessToken())) {
            //从微信端获取accessToken
            accessToken = getAccessTokenFromWx();
        } else {
            accessToken = response.getAccessToken();
        }
        return accessToken;

    }

    private String getAccessTokenFromWx() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenResponse> forEntity = restTemplate.getForEntity("https://api.weixin.qq" +
                        ".com/cgi-bin/token?grant_type=client_credential&appid=" + appId
                        + "&secret=" + appSecret,
                AccessTokenResponse.class);
        logger.info("getAccessTokenFromWx_AccessTokenResponse:{}", JSON.toJSONString(forEntity));
        AccessTokenResponse accessTokenResponse = forEntity.getBody();
        if (accessTokenResponse == null || StringUtils.isBlank(accessTokenResponse.getAccessToken())) {
            logger.error("getAccessTokenFromWx_error:获取accessToken出错");
            return null;
        } else {
            String key = "wxAccessToken_";
            stringRedisTemplate.opsForValue().set(key + "_jsapi", JSON.toJSONString(accessTokenResponse));
        }
        return accessTokenResponse.getAccessToken();
    }

    static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}

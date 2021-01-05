package com.shengsheng.wx.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * description:
 *
 * @author shengsheng
 * @date 2021/1/5 18:19
 */
public class ConsumeResponse extends BaseResponse {

    /**
     * errcode : 0
     * errmsg : ok
     * card : {"card_id":"pFS7Fjg8kV1IdDz01r4SQwMkuCKc"}
     * openid : oFS7Fjl0WsZ9AMZqrI80nbIq8xrA
     */
    @JSONField(name = "card")
    private CardBean card;
    @JSONField(name = "openid")
    private String openid;

    public CardBean getCard() {
        return card;
    }

    public void setCard(CardBean card) {
        this.card = card;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}

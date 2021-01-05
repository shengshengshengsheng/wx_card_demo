package com.shengsheng.wx.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * description:
 *
 * @author xuqiangsheng
 * @date 2021/1/5 18:11
 */
public class CheckCodeResponse extends BaseResponse {

    /**
     * errcode : 0
     * errmsg : ok
     * card : {"card_id":"pbLatjk4T4Hx-QFQGL4zGQy27_Qg","begin_time":1457452800,"end_time":1463155199}
     * openid : obLatjm43RA5C6QfMO5szKYnT3dM
     * can_consume : true
     * user_card_status : NORMAL
     */
    @JSONField(name = "card")
    private CardBean card;
    @JSONField(name = "openid")
    private String openid;
    @JSONField(name = "can_consume")
    private boolean canConsume;
    @JSONField(name = "user_card_status")
    private String userCardStatus;


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


    public boolean isCanConsume() {
        return canConsume;
    }

    public void setCanConsume(boolean canConsume) {
        this.canConsume = canConsume;
    }


    public String getUserCardStatus() {
        return userCardStatus;
    }

    public void setUserCardStatus(String userCardStatus) {
        this.userCardStatus = userCardStatus;
    }


}

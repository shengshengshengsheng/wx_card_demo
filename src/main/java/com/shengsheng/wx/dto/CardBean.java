package com.shengsheng.wx.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shengsheng
 */
public class CardBean {
        /**
         * card_id : pbLatjk4T4Hx-QFQGL4zGQy27_Qg
         * begin_time : 1457452800
         * end_time : 1463155199
         */

        @JSONField(name = "card_id")
        private String cardId;
        @JSONField(name = "begin_time")
        private int beginTime;
        @JSONField(name = "end_time")
        private int endTime;

        
        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        
        public int getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(int beginTime) {
            this.beginTime = beginTime;
        }

        
        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }
    }
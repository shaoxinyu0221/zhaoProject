package com.zhao.zhaoproject.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zhao.zhaoproject.entity.PointCard;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PointCardUtil {

    public static void setDefaultCardInfo(PointCard pointCard) {
        if (pointCard == null) {
            return;
        }
        pointCard.setId(UUID.randomUUID().toString());
        //如果没有编号，直接生成一个16位的随机数字
        if(StrUtil.isEmptyIfStr(pointCard.getCardNumber())){
            pointCard.setCardNumber(RandomUtil.randomNumbers(19));
        }
        //如果没设置密码，随机生成6位字符的密码
        if(StrUtil.isEmptyIfStr(pointCard.getPassword())){
            pointCard.setPassword(RandomUtil.randomString(6));
        }
        //如果金额没设置，直接剔除？
        if(ObjectUtil.isEmpty(pointCard.getAmount())){
            pointCard.setAmount(new BigDecimal("0"));
        }
        pointCard.setBalance(pointCard.getAmount());
        pointCard.setCreateTime(LocalDateTime.now());
        pointCard.setLastModifyTime(LocalDateTime.now());
        pointCard.setState(0);
        pointCard.setUsedAmount(new BigDecimal("0"));
    }


}

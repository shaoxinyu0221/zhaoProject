package com.zhao.zhaoproject.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.zhaoproject.entity.PointCard;
import com.zhao.zhaoproject.dao.mapper.PointCardMapper;
import com.zhao.zhaoproject.entity.dto.CreateCardDto;
import com.zhao.zhaoproject.service.IPointCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.zhaoproject.utils.PointCardUtil;
import com.zhao.zhaoproject.utils.ResultRet;
import com.zhao.zhaoproject.utils.ValidateUtil;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 邵鑫雨
 * @since 2024-10-19
 */
@Service
@Slf4j
public class PointCardServiceImpl extends ServiceImpl<PointCardMapper, PointCard> implements IPointCardService {

    @Resource
    private PointCardMapper pointCardMapper;

    @Override
    public ResultRet<Object> checkCard(List<PointCard> pointCards) {

        ResultRet<Object> resultRet = new ResultRet<>();




        return resultRet;
    }


    @Override
    public ResultRet<PointCard> getCardByNumber(String cardNumber) {
        ResultRet<PointCard> resultRet = new ResultRet<>();
        resultRet.setSuccess(false);

        QueryWrapper<PointCard> wrapper = new QueryWrapper<>();
        wrapper.eq("cardNumber", cardNumber);
        PointCard pointCard = pointCardMapper.selectOne(wrapper);

        if (Objects.isNull(pointCard)) {
            resultRet.setMessage("根据当前号码未找到积分卡信息");
            return resultRet;
        }
        pointCard.setPassword("*********");
        resultRet.setData(pointCard);
        resultRet.setSuccess(true);

        return resultRet;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultRet<List<BatchResult>> addPointCardBatch(List<PointCard> pointCards) {
        ResultRet<List<BatchResult>> resultRet = new ResultRet<>();

        for (PointCard pointCard : pointCards) {
            PointCardUtil.setDefaultCardInfo(pointCard);
        }

        try {
            List<BatchResult> insert = pointCardMapper.insert(pointCards);

            resultRet.setSuccess(true);
            resultRet.setCode(200);
            resultRet.setData(insert);
            return resultRet;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }


    @Override
    public ResultRet<String> createCardRandom(List<CreateCardDto> createCards) {
        ResultRet<String> resultRet = new ResultRet<>();
        resultRet.setSuccess(false);

        List<PointCard> pointCards = new ArrayList<>();
        //根据金额和张数的对应数据，创建积分卡
        for (CreateCardDto createCard : createCards) {
            if(StringUtil.isNullOrEmpty(createCard.getAmount()) || StringUtil.isNullOrEmpty(createCard.getCount())){
                continue;
            }

            //卡的张数必须是正整数,金额字段必须能被BigDecimal转换
            boolean positiveInteger = ValidateUtil.isPositiveInteger(createCard.getCount());
            boolean isMoney = Validator.isMoney(createCard.getAmount());
            if(positiveInteger && isMoney){
                for (int i = 0; i < Integer.parseInt(createCard.getCount()); i++) {
                    PointCard pointCard = new PointCard();
                    pointCard.setAmount(new BigDecimal(createCard.getAmount()));
                    PointCardUtil.setDefaultCardInfo(pointCard);
                    pointCards.add(pointCard);
                }
            }
        }

        try {
            pointCardMapper.insert(pointCards);
            resultRet.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultRet;
    }
}

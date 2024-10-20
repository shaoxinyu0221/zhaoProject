package com.zhao.zhaoproject.service;

import com.zhao.zhaoproject.entity.PointCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.zhaoproject.entity.dto.CreateCardDto;
import com.zhao.zhaoproject.utils.ResultRet;
import org.apache.ibatis.executor.BatchResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 邵鑫雨
 * @since 2024-10-19
 */
public interface IPointCardService extends IService<PointCard> {

    ResultRet<Object> checkCard(List<PointCard> pointCards);


    ResultRet<PointCard> getCardByNumber(String cardNumber);


    ResultRet<List<BatchResult>> addPointCardBatch(List<PointCard> pointCards);

    ResultRet<String> createCardRandom(List<CreateCardDto> createCards);
}

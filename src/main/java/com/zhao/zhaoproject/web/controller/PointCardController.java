package com.zhao.zhaoproject.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zhao.zhaoproject.entity.PointCard;
import com.zhao.zhaoproject.entity.dto.CreateCardDto;
import com.zhao.zhaoproject.service.IPointCardService;
import com.zhao.zhaoproject.utils.PointCardUtil;
import com.zhao.zhaoproject.utils.ResultRet;
import jakarta.annotation.Resource;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 邵鑫雨
 * @since 2024-10-19
 */
@RestController
@CrossOrigin
@RequestMapping("/zhaoProject/pointCard")
public class PointCardController {

    @Resource
    private IPointCardService pointCardService;

    /**
     * 批量添加积分卡
     * @param pointCards 积分卡列表
     * @return 结果集
     */
    @PostMapping("/addPointCardBatch")
    public ResultRet<List<BatchResult>> addPointCard(@RequestBody List<PointCard> pointCards) {
        ResultRet<List<BatchResult>> ret = new ResultRet<>();
        ret.setSuccess(Boolean.FALSE);

        if(CollectionUtil.isEmpty(pointCards)){
            ret.setCode(400);
            ret.setMessage("未获取到上传的积分卡列表，请重试");
            return ret;
        }

        return pointCardService.addPointCardBatch(pointCards);
    }

    /**
     * 添加积分卡
     * @param pointCard 积分卡
     * @return 结果集
     */
    @PostMapping("/addPointCard")
    public ResultRet<String> addPointCard(@RequestBody PointCard pointCard) {
        ResultRet<String> ret = new ResultRet<>();
        ret.setSuccess(Boolean.FALSE);

        if(ObjectUtil.isEmpty(pointCard)){
            ret.setCode(400);
            ret.setMessage("未获取到有效的卡片信息，请重新填写");
            return ret;
        }

        try {
            PointCardUtil.setDefaultCardInfo(pointCard);
            boolean save = pointCardService.save(pointCard);
            ret.setSuccess(save);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    /**
     * 根据面值和对应的张数，添加积分卡
     * @param params 添加条件
     * @return 结果集
     */
    @PostMapping("/addPointCardRandom")
    public String addPointCardRandom(@RequestBody Map<String,Object> params) {
        List<CreateCardDto> createCards = new ArrayList<>();
        ResultRet<String> ret = new ResultRet<>();
        ret.setSuccess(Boolean.FALSE);

        String createCardsJson = params.get("createCards").toString();

        try {
            createCards = JSONUtil.toBean(createCardsJson,new TypeReference<List<CreateCardDto>>() {},false);
        } catch (Exception e) {
            ret.setCode(400);
            ret.setMessage(e.getMessage());
            return JSONUtil.toJsonStr(ret);
        }

        if(CollectionUtil.isEmpty(createCards)){
            ret.setCode(400);
            ret.setMessage("对应的添加条件为空，请至少添加一条对应的条件");
            return JSONUtil.toJsonStr(ret);
        }

        ret = pointCardService.createCardRandom(createCards);
        ret.setMessage("赵金瑶");
        return  JSONUtil.toJsonStr(ret);
//        return "赵金瑶";
    }








    @PostMapping("/checkCardInfoByNumber")
    public ResultRet<PointCard> deletePointCard(String cardNumber) {
        ResultRet<PointCard> ret = new ResultRet<>();
        ret.setSuccess(Boolean.FALSE);

        if(StrUtil.isEmptyIfStr(cardNumber)){
            ret.setCode(400);
            ret.setMessage("卡片编号为空，请检查");
            return ret;
        }

        return pointCardService.getCardByNumber(cardNumber);
    }



}

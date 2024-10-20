package com.zhao.zhaoproject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 邵鑫雨
 * @since 2024-10-19
 */
@Getter
@Setter
@TableName("point_card")
public class PointCard implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cardNumber;

    private String password;

    /**
     * 默认值0没使用，1使用过一部分，2完全使用
     */
    private Integer state;

    /**
     * 绑定的用户id，当第一个用户使用时绑定，是否要用待定
     */
    private String userId;

    private BigDecimal amount;

    private BigDecimal balance;

    private BigDecimal usedAmount;

    private LocalDateTime createTime;

    private LocalDateTime lastModifyTime;
}

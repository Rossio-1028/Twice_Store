package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 闲置商品 实体对象
 *
 * @author: ShanZhu
 * @date: 2024-01-05
 */
@Data
public class IdleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 闲置物名称
     */
    private String idleName;

    /**
     * 详情
     */
    private String idleDetails;

    /**
     * 图集
     */
    private String pictureList;

    /**
     * 价格
     */
    private BigDecimal idlePrice;

    /**
     * 发货地区
     */
    private String idlePlace;

    /**
     * 分类标签
     */
    private Integer idleLabel;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 状态（发布1、下架2、删除0）
     */
    private Byte idleStatus;

    /**
     * 用户主键id
     */
    private Long userId;

    private User user;
}
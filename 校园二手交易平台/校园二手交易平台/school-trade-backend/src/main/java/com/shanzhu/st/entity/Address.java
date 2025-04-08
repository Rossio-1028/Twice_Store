package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 地址相关 实体对象
 *
 * @author: ShanZhu
 * @date: 2024-01-05
 */
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号
     */
    private String consigneePhone;

    /**
     * 省
     */
    private String provinceName;

    /**
     * 市
     */
    private String cityName;

    /**
     * 区
     */
    private String regionName;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 是否默认地址
     */
    private Boolean defaultFlag;

    /**
     * 用户主键id
     */
    private Long userId;


}
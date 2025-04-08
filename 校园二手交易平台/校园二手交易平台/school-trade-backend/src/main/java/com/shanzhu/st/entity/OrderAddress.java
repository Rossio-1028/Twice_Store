package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单地址 实体对象
 *
 * @author: ShanZhu
 * @date: 2024-01-05
 */
@Data
public class OrderAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private String consigneeName;

    private String consigneePhone;

    private String detailAddress;


}
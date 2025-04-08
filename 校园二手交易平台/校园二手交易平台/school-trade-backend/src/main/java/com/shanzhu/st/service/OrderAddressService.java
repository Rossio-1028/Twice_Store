package com.shanzhu.st.service;

import com.shanzhu.st.entity.OrderAddress;

/**
 * 订单地址 业务服务层接口
 *
 *
 */
public interface OrderAddressService {

    /**
     * 为订单添加地址信息
     *
     * @param orderAddress 订单地址信息
     * @return 结果
     */
    boolean addOrderAddress(OrderAddress orderAddress);

    /**
     * 更新订单的地址信息
     *
     * @param orderAddress 订单信息
     * @return 结果
     */
    boolean updateOrderAddress(OrderAddress orderAddress);

    /**
     * 获取订单的地址信息
     *
     * @param orderId 订单id
     * @return 订单地址信息
     */
    OrderAddress getOrderAddress(Long orderId);
}

package com.shanzhu.st.service;

import com.shanzhu.st.entity.Order;
import com.shanzhu.st.vo.PageVo;

import java.util.List;


/**
 * 订单 业务服务层接口
 *
 *
 */
public interface OrderService {

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    boolean addOrder(Order order);

    /**
     * 获取订单信息
     *
     * @param id 订单id
     * @return 订单信息
     */
    Order getOrder(Long id);

    /**
     * 更新订单信息
     *
     * @param order 订单信息
     * @return 更新结果
     */
    boolean updateOrder(Order order);

    /**
     * 获取某个用户买到的闲置的订单列表
     *
     * @param userId 用户id
     * @return 订单列表
     */
    List<Order> getMyOrder(Long userId);

    /**
     * 获取某个用户卖出的闲置的订单信息
     *
     * @param userId 用户id
     * @return 出售订单列表
     */
    List<Order> getMySoldIdle(Long userId);

    PageVo<Order> getAllOrder(int page, int nums);

    boolean deleteOrder(long id);
}

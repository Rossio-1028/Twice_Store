package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.OrderAddress;
import com.shanzhu.st.mapper.OrderAddressMapper;
import com.shanzhu.st.service.OrderAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
 *订单地址服务层实现类
 * 负责处理与订单地址相关的业务逻辑
 */

@Service
public class OrderAddressServiceImpl implements OrderAddressService {

    //使用@Resource注解注入OrderAddressMapper实例，用于与数据库进行交互
    @Resource
    private OrderAddressMapper orderAddressMapper;

    /*
     *添加订单地址的方法
     * orderAddressModel 要添加的订单地址对象
     * @return 如果插入成功返回true，否则返回false
     */
    public boolean addOrderAddress(OrderAddress orderAddressModel) {
        //调用orderAddressMapper的insert方法将订单地址对象插入数据库
        //判断插入操作的行数是否为1，如果为1则表示插入成功
        return orderAddressMapper.insert(orderAddressModel) == 1;
    }

    /*
     *更新订单地址的方法
     * orderAddressModel 要更新的订单地址对象
     * @return 如果插入成功返回true，否则返回false
     */
    public boolean updateOrderAddress(OrderAddress orderAddressModel) {
        //在更新时将订单ID设置为null，避免更新订单ID
        orderAddressModel.setOrderId(null);
        //调用orderAddressMapper的updateByPrimaryKeySelective方法根据主键选择性更新订单地址信息
        //判断更新操作影响的行数是否为1，如果为1则表示更新成功
        return orderAddressMapper.updateByPrimaryKeySelective(orderAddressModel) == 1;
    }

    /*
     *根据订单ID获取订单地址的方法
     * orderId 订单ID
     * @return 对应的订单地址对象，如果未找到就返回false
     */
    public OrderAddress getOrderAddress(Long orderId) {
        //调用orderAddressMapper的selectByOrderId方法根据订单ID查询订单地址信息
        return orderAddressMapper.selectByOrderId(orderId);
    }
}

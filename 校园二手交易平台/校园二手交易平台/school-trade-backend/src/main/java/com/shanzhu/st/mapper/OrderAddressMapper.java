package com.shanzhu.st.mapper;

import com.shanzhu.st.entity.OrderAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单地址 数据持久层
 *
 */
@Mapper
public interface OrderAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    OrderAddress selectByPrimaryKey(Long id);

    OrderAddress selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);
}
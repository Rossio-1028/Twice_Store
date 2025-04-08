package com.shanzhu.st.mapper;

import com.shanzhu.st.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单 数据持久层
 *
 */
@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    List<Order> getMyOrder(Long userId);

    List<Order> getAllOrder(int begin, int nums);

    int countAllOrder();

    List<Order> findOrderByIdleIdList(List<Long> idleIdList);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}
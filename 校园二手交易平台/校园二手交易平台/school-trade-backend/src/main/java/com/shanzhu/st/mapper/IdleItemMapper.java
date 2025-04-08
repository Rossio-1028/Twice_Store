package com.shanzhu.st.mapper;

import com.shanzhu.st.entity.IdleItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 闲置商品 数据持久层
 *
 */
@Mapper
public interface IdleItemMapper {
    //根据主键删除闲置商品
    int deleteByPrimaryKey(Long id);

    //插入闲置商品记录
    int insert(IdleItem record);

    //选择性插入闲置商品记录
    int insertSelective(IdleItem record);

    //根据主键查询闲置商品
    IdleItem selectByPrimaryKey(Long id);

    //获取指定用户的所有闲置商品，排除状态为0的商品
    List<IdleItem> getAllIdleItem(Long userId);

    //统计符合查询条件且状态为1的闲置商品数量
    int countIdleItem(String findValue);

    //统计指定标签且状态为1的闲置商品数量
    int countIdleItemByLable(int idleLabel);

    //统计指定状态的闲置商品数量
    int countIdleItemByStatus(int status);

    //根据搜索条件查询状态为1的闲置商品，支持分页
    List<IdleItem> findIdleItem(String findValue, int begin, int nums);

    //根据标签查询状态为1的闲置商品，支持分页
    List<IdleItem> findIdleItemByLable(int idleLabel, int begin, int nums);

    //根据状态查询闲置商品，支持分页
    List<IdleItem> getIdleItemByStatus(int status, int begin, int nums);

    //选择性更新闲置商品记录，只更新非空字段
    int updateByPrimaryKeySelective(IdleItem record);

    //更新闲置商品记录，更新所有字段
    int updateByPrimaryKey(IdleItem record);

    //根据ID列表查询闲置商品
    List<IdleItem> findIdleByList(List<Long> idList);
}
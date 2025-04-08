package com.shanzhu.st.service;

import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.vo.PageVo;

import java.util.List;

/**
 * 闲置物品 服务层接口
 * 调用IdleItemService.impl来实现具体的业务逻辑
 *
 */
public interface IdleItemService {

    /**
     * 发布闲置
     *
     * @param idleItem id
     * @return 添加结果
     */
    boolean addIdleItem(IdleItem idleItem);

    /**
     * 获取某个闲置的信息 --详情
     *
     * @param id id
     * @return 闲置信息
     */
    IdleItem getIdleItem(Long id);

    /**
     * 获取某个用户的所有闲置信息
     *
     * @param userId 用户id
     * @return 闲置商品列表
     */
    List<IdleItem> getAllIdelItem(Long userId);

    /**
     * 搜索闲置商品
     *
     * @param findValue 值
     * @param page      分页大小
     * @param nums      页数
     * @return 结果
     */
    PageVo<IdleItem> findIdleItem(String findValue, int page, int nums);

    /**
     * 按分类获取闲置，分页器
     *
     * @param idleLabel 标签
     * @param page      分页大小
     * @param nums      页数
     * @return 闲置商品
     */
    PageVo<IdleItem> findIdleItemByLable(int idleLabel, int page, int nums);

    /**
     * 更新闲置的状态信息
     *
     * @param idleItem 闲置商品
     * @return 结果
     */
    boolean updateIdleItem(IdleItem idleItem);

    /**
     * 管理员获取闲置商品列表
     *
     *
     * @return 结果
     */
    PageVo<IdleItem> adminGetIdleList(int status, int page, int nums);
}

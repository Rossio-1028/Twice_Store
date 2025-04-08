package com.shanzhu.st.service;

import com.shanzhu.st.entity.Favorite;

import java.util.List;

/**
 * 收藏 业务服务层接口
 *
 *
 */
public interface FavoriteService {

    /**
     * 添加收藏
     *
     * @param favorite 收藏信息
     * @return 结果
     */
    boolean addFavorite(Favorite favorite);

    /**
     * 取消收藏
     *
     * @param id id
     * @return 结果
     */
    boolean deleteFavorite(Long id);

    /**
     * 判断是否收藏
     *
     * @param userId 用户id
     * @param idleId 闲置商品id
     * @return 结果
     */
    Integer isFavorite(Long userId, Long idleId);

    /**
     * 获取收藏列表
     *
     * @param userId 用户id
     * @return 收藏列表
     */
    List<Favorite> getAllFavorite(Long userId);
}

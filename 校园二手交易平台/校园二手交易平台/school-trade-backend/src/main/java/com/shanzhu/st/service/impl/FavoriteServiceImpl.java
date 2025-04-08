package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.Favorite;
import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.mapper.FavoriteMapper;
import com.shanzhu.st.mapper.IdleItemMapper;
import com.shanzhu.st.service.FavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏 服务层接口的实现类
 *
 */

@Service
public class FavoriteServiceImpl implements FavoriteService {
    //使用@Resource注解注入FavoriteMapper实例，用于操作Favorite表
    @Resource
    private FavoriteMapper favoriteMapper;
    //使用@Resource注解注入IdleItemMapper实例，用于操作IdleItem表
    @Resource
    private IdleItemMapper idleItemMapper;

    /**
     * 添加收藏记录
     * favorite 要添加的收藏对象
     * @return 如果插入成功返回true,否则返回false
     */
    public boolean addFavorite(Favorite favorite) {
        return favoriteMapper.insert(favorite) == 1;
    }

    /**
     * 删除指定ID的收藏记录
     * id 要删除的收藏记录的ID
     * 根据主键删除记录
     * @return 如果删除成功返回true,否则返回false
     */
    public boolean deleteFavorite(Long id) {
        return favoriteMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 检查用户是否收藏了某个闲置商品
     * userId 用户ID
     * idleId 闲置商品ID
     * @return 如果用户已经收藏返回非零值，否则返回0
     */
    public Integer isFavorite(Long userId, Long idleId) {
        return favoriteMapper.checkFavorite(userId, idleId);
    }

    /**
     * 获取某个用户的所有收藏记录
     * userId 用户ID
     * @return 包含该用户的所有收藏记录的列表
     */
    public List<Favorite> getAllFavorite(Long userId) {
        //调用favoriteMapper的getMyFavorite方法获取该用户的所有收藏记录
        List<Favorite> list = favoriteMapper.getMyFavorite(userId);
        if (list.size() > 0) {
            //创建一个存储闲置商品ID的列表
            List<Long> idleIdList = new ArrayList<>();
            //遍历收藏记录列表，将每个收藏记录中的闲置物品ID添加到idleIdList中
            for (Favorite i : list) {
                idleIdList.add(i.getIdleId());
            }
            //调用idleItemMapper的findIdleByList方法根据闲置商品ID列表查询对应的闲置商品信息
            List<IdleItem> idleItemList = idleItemMapper.findIdleByList(idleIdList);
            //创建一个映射，用于存储物品ID与对应的闲置商品对象
            Map<Long, IdleItem> map = new HashMap<>();
            //遍历闲置商品列表，将每个闲置商品的ID作为键，闲置商品对象作为值存入映射中
            for (IdleItem idle : idleItemList) {
                map.put(idle.getId(), idle);
            }
            //遍历收藏记录列表，为每个收藏记录设置对应的闲置物品信息
            for (Favorite i : list) {
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        //返回包含该用户所有收藏记录的列表
        return list;
    }

}

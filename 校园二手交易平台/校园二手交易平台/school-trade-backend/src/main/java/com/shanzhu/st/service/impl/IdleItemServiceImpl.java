package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.entity.User;
import com.shanzhu.st.mapper.IdleItemMapper;
import com.shanzhu.st.mapper.UserMapper;
import com.shanzhu.st.service.IdleItemService;
import com.shanzhu.st.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IdleItemServiceImpl implements IdleItemService {

    //使用@Resource注解注入IdleItemMapper实例，用于与数据库中IdleItem表进行交互
    @Resource
    private IdleItemMapper idleItemMapper;

    //使用@Resource注解注入UserMapper实例，用于与数据库中的User表交互
    @Resource
    private UserMapper userMapper;

    /*
     *添加闲置商品
     * idleItem --要添加的闲置对象
     * @return 添加成功，返回true;失败，返回false
     */
    public boolean addIdleItem(IdleItem idleItem) {
        //调用idleItemMapper的insert方法插入闲置商品信息，并判断是否插入成功
        return idleItemMapper.insert(idleItem) == 1;
    }

    /*
     *根据闲置物品ID获取闲置商品信息 --详情
     * id --闲置商品ID
     * @return 添返回对应的闲置商品对象
     */
    public IdleItem getIdleItem(Long id) {
        //调用idleItemMapper的selectByPrimaryKey方法，根据闲置商品ID获取闲置信息
        IdleItem idleItem = idleItemMapper.selectByPrimaryKey(id);
        if (idleItem != null) {
            //如果查询到闲置商品信息，根据其用户ID查询对应的用户信息，并设置到闲置商品对象中
            idleItem.setUser(userMapper.selectByPrimaryKey(idleItem.getUserId()));
        }
        return idleItem;
    }

    /*
     *获取用户所有闲置物品信息
     * userId  用户的ID
     * @return 返回该用户的所有闲置商品列表
     */
    public List<IdleItem> getAllIdelItem(Long userId) {
        //调用idleItemMapper的getAllIdleItem方法来查询
        return idleItemMapper.getAllIdleItem(userId);
    }

    /*
     *查询闲置商品，并分页展示
     * findValue 查找的关键词
     * @return 返回符合查询条件的闲置商品列表和总记录数的分页对象
     */
    public PageVo<IdleItem> findIdleItem(String findValue, int page, int nums) {
        //调用idleItemMapper的findIdleItem方法查询符合关键词的闲置商品，并进行分页
        List<IdleItem> list = idleItemMapper.findIdleItem(findValue, (page - 1) * nums, nums);
        if (list.size() > 0) {
            //如果查询到闲置商品信息，将所有闲置商品的用户ID收集到一个列表中
            List<Long> idList = new ArrayList<>();
            for (IdleItem i : list) {
                idList.add(i.getUserId());
            }
            //根据用户ID列表查询对应的用户信息
            List<User> userList = userMapper.findUserByList(idList);
            //将用户信息存储到一个Map中，键为用户ID，值为用户对象
            Map<Long, User> map = new HashMap<>();
            for (User user : userList) {
                map.put(user.getId(), user);
            }
            //为每一个闲置物品对象设置对应的用户信息
            for (IdleItem i : list) {
                i.setUser(map.get(i.getUserId()));
            }
        }
        //调用idleItemMapper的countIdleItem方法，统计符合关键词的闲置商品总数
        int count = idleItemMapper.countIdleItem(findValue);
        return new PageVo<>(list, count);
    }

    /*
     * 根据标签查找闲置商品，并进行分页处理
     * idleLabel 分类标签
     * @return 符合条件的闲置物品列表和总记录数的分页对象
     */
    public PageVo<IdleItem> findIdleItemByLable(int idleLabel, int page, int nums) {
        //调用idleItemMapper的findIdleItemByLable方法查询符合条件的闲置商品，并进行分页
        List<IdleItem> list = idleItemMapper.findIdleItemByLable(idleLabel, (page - 1) * nums, nums);
        if (list.size() > 0) {
            //查询到的闲置商品，将所有对应的用户ID收集到一个列表中
            List<Long> idList = new ArrayList<>();
            for (IdleItem i : list) {
                idList.add(i.getUserId());
            }
            //根据用户ID列表查询对应的用户信息
            List<User> userList = userMapper.findUserByList(idList);
            //将用户信息存储到一个Map中，键为用户ID，值为用户对象
            Map<Long, User> map = new HashMap<>();
            for (User user : userList) {
                map.put(user.getId(), user);
            }
            //为每一个闲置商品对象设置对应的用户信息
            for (IdleItem i : list) {
                i.setUser(map.get(i.getUserId()));
            }
        }
        //调用idleItemMapper的countIdleItemByLable方法统计符合标签的闲置商品总数
        int count = idleItemMapper.countIdleItemByLable(idleLabel);
        //创建并返回包含闲置商品列表和总记录数的分页对象
        return new PageVo<>(list, count);
    }

    /*
     *更新闲置商品信息
     * idleItem 要更新的闲置商品对象
     * @return 如果更新成功返回true，否则返回false
     */
    public boolean updateIdleItem(IdleItem idleItem) {
        //调用idleItemMapper的updateByPrimaryKeySelective方法，并判断是否成功
        return idleItemMapper.updateByPrimaryKeySelective(idleItem) == 1;
    }

    /*
     *管理员根据状态获取闲置商品列表，并进行分页处理
     * status 闲置商品的状态
     * @return 包含闲置商品列表和总记录数的分页对象
     */
    public PageVo<IdleItem> adminGetIdleList(int status, int page, int nums) {
        //调用idleItemMapper的getIdleItemByStatus方法查询符合状态的闲置商品，进行分页
        List<IdleItem> list = idleItemMapper.getIdleItemByStatus(status, (page - 1) * nums, nums);
        if (list.size() > 0) {
            //查询到闲置商品，将其用户ID收集到一个列表中
            List<Long> idList = new ArrayList<>();
            for (IdleItem i : list) {
                idList.add(i.getUserId());
            }
            //根据用户ID列表查询对应的用户信息
            List<User> userList = userMapper.findUserByList(idList);
            //将用户信息存储到一个Map中，键为用户ID，值为用户对象
            Map<Long, User> map = new HashMap<>();
            for (User user : userList) {
                map.put(user.getId(), user);
            }
            //为每个闲置商品设置对应的用户信息
            for (IdleItem i : list) {
                i.setUser(map.get(i.getUserId()));
            }
        }
        //调用idleItemMapper的countIdleItemByStatus方法统计符合状态的闲置物品总数
        int count = idleItemMapper.countIdleItemByStatus(status);
        //创建并返回包含闲置商品列表和总记录数的分页对象
        return new PageVo<>(list, count);
    }

}

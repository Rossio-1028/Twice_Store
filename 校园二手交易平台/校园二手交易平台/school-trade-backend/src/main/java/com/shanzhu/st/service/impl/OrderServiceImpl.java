package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.entity.Order;
import com.shanzhu.st.mapper.IdleItemMapper;
import com.shanzhu.st.mapper.OrderMapper;
import com.shanzhu.st.service.OrderService;
import com.shanzhu.st.utils.OrderTask;
import com.shanzhu.st.utils.OrderTaskHandler;
import com.shanzhu.st.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/*
 *订单服务层的实现类 主要负责处理与订单相关的业务逻辑
 * 4表示未支付
 * 1表示已支付
 */

@Service
public class OrderServiceImpl implements OrderService {

    //注入OrderMapper实例，用于操作数据库Order表
    @Resource
    private OrderMapper orderMapper;

    //注入IdleItemMapper实例，用于操作数据库idleItem表
    @Resource
    private IdleItemMapper idleItemMapper;

    //静态HashMap 用于存储ReentrantLock对象，用于并发控制
    private static HashMap<Integer, ReentrantLock> lockMap = new HashMap<>();

    //静态代码块，初始化lockMap，创建100个ReentrantLock对象
    static {
        for (int i = 0; i < 100; i++) {
            lockMap.put(i, new ReentrantLock(true));
        }
    }

    /*添加订单
     *order 要添加的订单对象
     * 1表示可售
     * 2表示不可售（待支付或者待发货）
     * @return 添加成功返回true,失败返回false
     */
    public boolean addOrder(Order order) {
        //根据订单中的闲置商品ID查询闲置商品信息
        IdleItem idleItemModel = idleItemMapper.selectByPrimaryKey(order.getIdleId());
        //检查闲置商品的状态是否为可售状态（状态码为 1 ）
        if (idleItemModel.getIdleStatus() != 1) {
            return false;
        }
        //创建一个新的IdleItem对象，用于更新闲置商品状态
        IdleItem idleItem = new IdleItem();
        idleItem.setId(order.getIdleId());
        idleItem.setUserId(idleItemModel.getUserId());
        idleItem.setIdleStatus((byte) 2);

        //根据闲置商品ID计算锁的索引
        int key = (int) (order.getIdleId() % 100);
        //获取对应的ReentrantLock对象
        ReentrantLock lock = lockMap.get(key);
        boolean flag;
        try {
            //获取锁
            lock.lock();
            //调用辅助方法添加订单
            flag = addOrderHelp(idleItem, order);
        } finally {
            //释放锁
            lock.unlock();
        }
        return flag;
    }

    /*
     *添加订单的辅助方法，使用事务管理
     * idleItem 要更新的闲置商品对象
     * order 要添加的订单对象
     * @return 添加成功返回true,失败返回false
     */
    @Transactional(rollbackFor = Exception.class) //声明式事务管理注解
    public boolean addOrderHelp(IdleItem idleItem, Order order) {
        //再次检查闲置商品的状态是否为可售状态
        IdleItem idleItemModel = idleItemMapper.selectByPrimaryKey(order.getIdleId());
        if (idleItemModel.getIdleStatus() != 1) {
            return false;
        }
        //更新闲置商品状态
        if (idleItemMapper.updateByPrimaryKeySelective(idleItem) == 1) {
            //插入订单信息
            if (orderMapper.insert(order) == 1) {
                //设置订单状态为待支付
                order.setOrderStatus((byte) 4);
                //添加订单任务，半小时未支付则取消订单
                OrderTaskHandler.addOrder(new OrderTask(order, 30 * 60));
                return true;
            } else {
                //插入订单失败，抛出异常出发事务回滚
                new RuntimeException();
            }
        }
        return false;
    }

    /*
     *根据订单ID获取订单信息
     * id 订单ID
     * @return 订单对象
     */
    public Order getOrder(Long id) {
        //根据订单ID查询订单信息，调用orderMapper的selectByPrimaryKey方法，并存储到order实体类中
        Order order = orderMapper.selectByPrimaryKey(id);
        //根据订单中的闲置商品ID查询闲置商品信息，并设置到订单对象中
        order.setIdleItem(idleItemMapper.selectByPrimaryKey(order.getIdleId()));
        return order;
    }

     /*
      *更新订单信息，使用事务管理
      * order 要更新的订单对象
      * @return 更新成功返回true,失败返回false
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(Order order) {
        //不可修改的信息
        order.setOrderNumber(null);
        order.setUserId(null);
        order.setIdleId(null);
        order.setCreateTime(null);
        //如果订单状态为取消订单
        if (order.getOrderStatus() == 4) {
            //取消订单,需要优化，减少数据库查询次数
            //根据订单ID查询订单信息
            Order o = orderMapper.selectByPrimaryKey(order.getId());
            //检查订单状态是否为未支付状态
            if (o.getOrderStatus() != 0) {
                return false;
            }
            //根据订单中的闲置商品ID查询闲置商品信息
            IdleItem idleItemModel = idleItemMapper.selectByPrimaryKey(o.getIdleId());
            //检查闲置商品状态是否为已预定状态
            if (idleItemModel.getIdleStatus() == 2) {
                //创建一个新的IdleItem对象，用于更新闲置商品状态为可售状态
                IdleItem idleItem = new IdleItem();
                idleItem.setId(o.getIdleId());
                idleItem.setUserId(idleItemModel.getUserId());
                idleItem.setIdleStatus((byte) 1);
                //更新订单信息
                if (orderMapper.updateByPrimaryKeySelective(order) == 1) {
                    //更新闲置商品状态
                    if (idleItemMapper.updateByPrimaryKeySelective(idleItem) == 1) {
                        return true;
                    } else {
                        //更新闲置商品状态失败，抛出异常触发事务回滚
                        new RuntimeException();
                    }
                }
                return false;
            } else {
                //直接更新订单信息
                if (orderMapper.updateByPrimaryKeySelective(order) == 1) {
                    return true;
                } else {
                    new RuntimeException();
                }
            }
        }
        //直接更新订单信息
        return orderMapper.updateByPrimaryKeySelective(order) == 1;
    }

    /*
     *根据用户ID获取用户的订单列表
     * userId 用户ID
     * @return 订单列表
     */
    public List<Order> getMyOrder(Long userId) {
        //根据用户ID查询订单列表 调用orderMapper的getMyOrder方法
        List<Order> list = orderMapper.getMyOrder(userId);
        if (list.size() > 0) {
            //提取订单中闲置商品的ID列表
            List<Long> idleIdList = new ArrayList<>();
            for (Order i : list) {
                idleIdList.add(i.getIdleId());
            }
            //根据闲置商品ID列表查询闲置商品信息列表
            List<IdleItem> idleItemList = idleItemMapper.findIdleByList(idleIdList);
            //将闲置商品信息存储到Map中，方便查找
            Map<Long, IdleItem> map = new HashMap<>();
            for (IdleItem idle : idleItemList) {
                map.put(idle.getId(), idle);
            }
            //遍历，将闲置商品信息设置到每个订单对象中
            for (Order i : list) {
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        return list;
    }

    /*
     *根据用户ID获取用户出售的闲置商品对应的订单列表，使用事务管理
     * userId 用户ID
     * @return 订单列表
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Order> getMySoldIdle(Long userId) {
        //根据用户ID查询用户出售的闲置商品列表，调用idleItemMapper的getAllIdleItem方法
        List<IdleItem> list = idleItemMapper.getAllIdleItem(userId);
        List<Order> orderList = null;
        if (list.size() > 0) {
            //提取闲置商品列表
            List<Long> idleIdList = new ArrayList<>();
            for (IdleItem i : list) {
                idleIdList.add(i.getId());
            }
            //根据闲置商品ID列表查询订单列表
            orderList = orderMapper.findOrderByIdleIdList(idleIdList);
            //将闲置商品信息存储到Map中，方便查找
            Map<Long, IdleItem> map = new HashMap<>();
            for (IdleItem idle : list) {
                map.put(idle.getId(), idle);
            }
            //遍历，将闲置商品信息设置到每一个订单对象中
            for (Order o : orderList) {
                o.setIdleItem(map.get(o.getIdleId()));
            }
        }
        return orderList;
    }

    /*
     * 分页获取所有订单信息
     * page 页码
     * nums 每页记录数
     * @return 分页订单信息对象
     */
    public PageVo<Order> getAllOrder(int page, int nums) {
        //根据页码和每页数量查询订单列表，调用orderMapper的getAllOrder方法，进行分页
        List<Order> list = orderMapper.getAllOrder((page - 1) * nums, nums);
        if (list.size() > 0) {
            //提取订单中的闲置商品ID列表
            List<Long> idleIdList = new ArrayList<>();
            for (Order i : list) {
                idleIdList.add(i.getIdleId());
            }
            //根据闲置商品ID列表查询闲置商品信息列表
            List<IdleItem> idleItemList = idleItemMapper.findIdleByList(idleIdList);
            //将闲置商品信息存储到Map中，方便查找
            Map<Long, IdleItem> map = new HashMap<>();
            for (IdleItem idle : idleItemList) {
                map.put(idle.getId(), idle);
            }
            //遍历，将闲置商品信息设置到每个订单对象中
            for (Order i : list) {
                i.setIdleItem(map.get(i.getIdleId()));
            }
        }
        //查询订单总数，调用orderMapper的countAllOrder方法
        int count = orderMapper.countAllOrder();
        //创建分页对象并返回
        return new PageVo<>(list, count);
    }

    /*
     * 根据订单ID删除订单信息
     * id 订单ID
     * @return 删除成功返回true，失败返回false
     */
    public boolean deleteOrder(long id) {
        //根据订单ID删除订单信息，调用orderMapper的deleteByPrimaryKey方法
        return orderMapper.deleteByPrimaryKey(id) == 1;
    }
}

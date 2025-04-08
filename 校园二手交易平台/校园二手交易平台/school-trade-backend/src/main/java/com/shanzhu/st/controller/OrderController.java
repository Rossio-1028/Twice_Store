package com.shanzhu.st.controller;

import com.shanzhu.st.entity.Order;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.OrderService;
import com.shanzhu.st.utils.IdFactoryUtil;
import com.shanzhu.st.utils.OrderTaskHandler;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 订单 控制层
 * 负责处理与订单相关的HTTP请求，包括添加、查询、更新订单等操作
 *
 */
@CrossOrigin  //允许跨域请求
@RestController //表明这个是RESTful风格的控制器
@RequestMapping("/order")  //这个控制器的基础请求URL路径：/order
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 添加订单
     *
     * shUserId 用户id 从cookie中获取
     * order    订单信息，从请求体中获取
     * @return 添加结果 使用R封装返回信息
     */
    @PostMapping("/add")
    public R addOrder(
            //从cookie中获取ShUserId
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            //从请求体中获取订单信息
            @RequestBody Order order
    ) {
        if (OrderTaskHandler.orderService == null) {
            //初始化OrderTaskHandler的orderService
            OrderTaskHandler.orderService = orderService;
        }
        //生成订单号
        order.setOrderNumber(IdFactoryUtil.getOrderId());
        //设置订单创建时间为当前时间
        order.setCreateTime(new Date());
        //设置订单所属用户id
        order.setUserId(Long.valueOf(shUserId));
        //设置订单状态为初始状态
        order.setOrderStatus((byte) 0);
        //设置订单支付为未支付状态
        order.setPaymentStatus((byte) 0);
        //调用orderService服务层的addOrder方法添加订单
        if (orderService.addOrder(order)) {
            //添加成功，返回成功信息和订单信息
            return R.success(order);
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 获取订单信息
     *
     * shUserId 用户id 从cookie中获取
     * id       订单id 从请求参数中获取
     * @return 订单信息 使用R类封装返回信息
     */
    @GetMapping("/info")
    public R getOrderInfo(
            //从cookie中获取ShUserId
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestParam Long id  //从请求参数中获取订单id
    ) {
        //调用orderService层的getOrder方法获取订单信息，并存放到订单实体类中
        Order order = orderService.getOrder(id);
        if (order.getUserId().equals(Long.valueOf(shUserId)) ||
                order.getIdleItem().getUserId().equals(Long.valueOf(shUserId))) {
            //如果订单所属用户id或闲置商品所属用户id等于当前用户id
            //返回成功信息和订单信息
            return R.success(order);
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 更新订单信息
     *
     * shUserId 用户id 从cookie中获取
     * order    订单信息 从请求体中获取
     * @return 更新结果 使用R类封装返回信息
     */
    @PostMapping("/update")
    public R updateOrder(
            //从cookie中获取ShUserId
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestBody Order order  //从请求体中获取订单信息
    ) {
        if (order.getPaymentStatus() != null && order.getPaymentStatus().equals((byte) 1)) {
            //如果订单已支付，设置支付时间为当前时间
            order.setPaymentTime(new Date());
        }
        //调用orderService层的updateOrder方法更新订单信息
        if (orderService.updateOrder(order)) {
            //更新成功，返回成功信息和订单信息
            return R.success(order);
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 获取我的订单
     *
     * shUserId 用户id 从cookie中获取
     * @return 订单信息 使用R类封装返回信息
     */
    @GetMapping("/my")
    public R getMyOrder(
            //从cookie中获取ShUserId
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId
    ) {
        //调用orderService层的getMyOrder方法获取用户的订单信息，并返回成功信息和订单列表
        return R.success(orderService.getMyOrder(Long.valueOf(shUserId)));
    }

    /**
     * 获取我的出售信息
     *
     * shUserId 用户id 从cookie中获取
     * @return 出售信息 使用R类封装并返回信息
     */
    @GetMapping("/my-sold")
    public R getMySoldIdle(
            //从cookie中获取ShUserId
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId
    ) {
        //调用orderService层的getMySoldIdle方法来获取用户出售信息，并返回出售列表
        return R.success(orderService.getMySoldIdle(Long.valueOf(shUserId)));
    }
}

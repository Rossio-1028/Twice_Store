package com.shanzhu.st.controller;

import com.shanzhu.st.entity.OrderAddress;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.OrderAddressService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 订单地址 控制层
 * 负责接收与订单地址相关的HTTP请求，并将请求转发给对应的服务层方法进行处理
 * 最终将处理结果封装成统一的响应格式返回给用户端
 *
 */
@CrossOrigin //允许跨域请求
@RestController  //RESTful风格的控制器，会自动将方法转换成JSON格式
@RequestMapping("/order-address")  //该控制器处理的所有请求URL都以 /order-address开头
public class OrderAddressController {

    //@Resource注解注入订单地址服务层的实例，用于调用服务层的方法处理业务逻辑
    @Resource
    private OrderAddressService orderAddressService;

    /**
     * 添加订单地址信息
     *
     * orderAddress 地址信息，从请求体中获取，包含订单地址的详细信息
     * shUserId 用户ID 从cookie中获取
     * @return 结果 封装了处理结果的统一响应对象R
     */
    @PostMapping("/add")
    public R addOrderAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @RequestBody OrderAddress orderAddress //从请求体中获取订单地址对象
    ) {
        //调用orderAddressService的addOrderAddress方法来添加订单地址信息，将结果封装在R对象中返回
        return R.success(orderAddressService.addOrderAddress(orderAddress));
    }

    /**
     * 更新订单地址信息
     *
     * orderAddress 订单地址信息，从请求体中获取，包含要更新的订单地址详细信息
     * shUserId 用户ID，从cookie中获取
     * @return 更新结果
     */
    @PostMapping("/update")
    public R updateOrderAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @RequestBody OrderAddress orderAddress  //从请求体中获取订单地址对象
    ) {
        //调用orderAddressService的updateOrderAddress方法更新订单地址信息
        if (orderAddressService.updateOrderAddress(orderAddress)) {
            //如果成功更新，将更新后的订单地址信息封装在R对象中返回
            return R.success(orderAddress);
        }
        //更新失败，返回包含系统错误信息的R对象
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 获取订单地址信息
     * orderId 订单ID，从请求参数中那个获取
     * shUserId 用户id，从cookie中获取，
     * @return 订单地址信息，封装在R对象中并返回
     */
    @GetMapping("/info")
    public R getOrderAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @RequestParam Long orderId  //从请求参数中获取订单ID
    ) {
        //调用orderAddressService的getOrderAddress方法获取订单地址信息，并将结果封装在R对象中返回
        return R.success(orderAddressService.getOrderAddress(orderId));
    }
}

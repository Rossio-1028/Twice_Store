package com.shanzhu.st.controller;

import com.shanzhu.st.entity.Address;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.AddressService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 地址相关 控制层
 *负责处理与地址信息相关的HTTP请求，包括查询、添加和删除操作
 */
@CrossOrigin  //允许跨域请求
@RestController  //RESTful风格的控制器，返回的结果会自动转换为JSON格式
@RequestMapping("/address")   //该控制器处理的所有请求的URL前缀是 /address
public class AddressController {

    //通过@Resource注解注入AddressService实例，用于调用业务逻辑方法
    @Resource
    private AddressService addressService;

    /**
     * 通过id查询地址
     *
     * @param shUserId 用户id 从cookie中获取，验证用户的登录状态
     * @param id       地址id
     * @return 地址信息 封装在R对象中返回给前端
     */
    @GetMapping("/info")
    public R getAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestParam(value = "id", required = false) Long id
    ) {
        if (null == id) {
            //如果地址id为空，则调用addressService的getAddressByUser方法查询该用户的所有地址
            return R.success(addressService.getAddressByUser(Long.valueOf(shUserId)));
        } else {
            //如果地址id不为空，则调用addressService的getAddressById方法查询指定用户id的地址
            return R.success(addressService.getAddressById(id, Long.valueOf(shUserId)));
        }
    }

    /**
     * 添加地址
     *
     * @param shUserId 用户id 从cookie中获取
     * @param address  地址信息 从请求体中获取
     * @return 结果 封装在R对象中返回给前端
     */
    @PostMapping("/add")
    public R addAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestBody Address address  //从请求体中获取Address对象
    ) {
        address.setUserId(Long.valueOf(shUserId)); //设置地址的用户id
        if (addressService.addAddress(address)) {
            //如果添加地址成功，就返回成功信息和添加的地址信息
            return R.success(address);
        }
        //添加失败，返回失败信息
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 更新地址信息
     *
     * @param shUserId 用户id 从cookie中获取
     * @param address  地址信息 从请求体中获取
     * @return 结果 封装在R对象中返回给前端
     */
    @PostMapping("/update")
    public R updateAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestBody Address address  //从请求体中获取Address对象
    ) {
        address.setUserId(Long.valueOf(shUserId));
        if (addressService.updateAddress(address)) {
            //更新地址成功，返回成功信息
            return R.success();
        }
        //更新失败，返回失败信息
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 删除地址
     *
     * @param shUserId 用户id 从cookie中获取
     * @param address  地址信息 从请求体中获取
     * @return 结果 封装在R对象中返回给前端
     */
    @PostMapping("/delete")
    public R deleteAddress(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestBody Address address //从请求体中获取Address对象
    ) {
        //设置地址的用户id
        address.setUserId(Long.valueOf(shUserId));
        if (addressService.deleteAddress(address)) {
            //删除成功，返回成功信息
            return R.success();
        }
        //删除失败，返回失败信息
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

}

package com.shanzhu.st.controller;
import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.IdleItemService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 闲置商品 控制层
 *主要负责接收闲置商品相关的HTTP请求，调用相应的服务层方法处理业务逻辑
 *将结果以统一的响应格式返回用户端
 */

@CrossOrigin
@RestController
@RequestMapping("idle")  //指定该控制器处理的请求路径前缀是 /idle
public class IdleItemController {

    @Resource
    private IdleItemService idleItemService;

    /**
     * 添加闲置商品
     *
     * @param shUserId 用户id
     * @param idleItem 闲置id
     * @return 结果
     */
    @PostMapping("add")
    public R addIdleItem(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestBody IdleItem idleItem  //新增闲置商品的信息从请求体中获取
    ) {
        //获取发布闲置商品的用户ID
        idleItem.setUserId(Long.valueOf(shUserId));
        //设置闲置商品的状态 = 1
        idleItem.setIdleStatus((byte) 1);
        //设置闲置商品的发布时间为当前时间
        idleItem.setReleaseTime(new Date());
        //调用idleItemService层的addIdleItem方法
        if (idleItemService.addIdleItem(idleItem)) {
            //添加成功，返回包括数据在内的成功响应
            return R.success(idleItem);
        }
        //失败，返回系统错误信息
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 获取闲置信息--详情
     *
     * @param id 闲置id --请求参数中获取
     * @return 闲置信息
     */
    @GetMapping("info")
    public R getIdleItem(@RequestParam Long id) {
        return R.success(idleItemService.getIdleItem(id));
    }

    /**
     * 查询用户所有闲置信息
     *
     * @param shUserId 用户id 从请求的cookie中获取
     * @return 闲置信息
     */
    @GetMapping("all")
    public R getAllIdleItem(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId
    ) {
        //调用服务层的getAllIdelItem方法，并返回成功信息的响应
        return R.success(idleItemService.getAllIdelItem(Long.valueOf(shUserId)));
    }

    /**
     * 查询限制
     *
     * @param findValue 值 关键字，从请求参数中获取
     * @param page      分页大小
     * @param nums      每页显示的记录数，默认为8
     * @return 返回符合查询条件的闲置商品信息的响应
     */
    @GetMapping("find")
    public R findIdleItem(
            @RequestParam(value = "findValue", required = false) String findValue,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nums", required = false) Integer nums
    ) {
        //如果关键字为空，就设置空字符串
        if (null == findValue) {
            findValue = "";
        }
        int p = 1;
        int n = 8;
        //如果传入了分页大小，需要进行判断
        if (null != page) {
            p = page > 0 ? page : 1;
        }
        //如果传入了记录数，需要进行判断
        if (null != nums) {
            n = nums > 0 ? nums : 8;
        }
        //调用服务层的findIdleItem方法进行查询，并返回成功响应
        return R.success(idleItemService.findIdleItem(findValue, p, n));
    }

    /**
     * 查询标签
     //@param idleLabel  闲置商品的标签ID，从请求参数中获取，必须传入
     * @return 结果，成功则返回符合标签条件的闲置商品信息的响应
     */
    @GetMapping("lable")
    public R findIdleItemByLable(
            @RequestParam(value = "idleLabel", required = true) Integer idleLabel,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nums", required = false) Integer nums
    ) {
        int p = 1;
        int n = 8;
        if (null != page) {
            p = page > 0 ? page : 1;
        }
        if (null != nums) {
            n = nums > 0 ? nums : 8;
        }
        //调用服务层的findIdleItemByLable方法
        return R.success(idleItemService.findIdleItemByLable(idleLabel, p, n));
    }

    /**
     * 更新信息
     *
     * @param shUserId 用户id 从请求的cookie中获取
     * @param idleItem 闲置物品信息 从请求体中获取
     * @return 更新结果
     */
    @PostMapping("update")
    public R updateIdleItem(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            @RequestBody IdleItem idleItem
    ) {
        //设置修改闲置商品的用户ID
        idleItem.setUserId(Long.valueOf(shUserId));
        //调用服务层的updateIdleItem方法进行更新操作
        if (idleItemService.updateIdleItem(idleItem)) {
            //更新成功，则返回成功响应
            return R.success();
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

}

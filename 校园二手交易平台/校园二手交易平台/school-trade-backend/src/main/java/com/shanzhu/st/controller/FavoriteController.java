package com.shanzhu.st.controller;

import com.shanzhu.st.entity.Favorite;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.FavoriteService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 收藏 控制层
 *负责处理与收藏功能相关的HTTP请求，包括添加收藏、删除收藏、检查收藏状态和获取用户的收藏列表等操作
 */
@CrossOrigin //允许跨域请求
@RestController //表明这是一个RESTful风格的控制器，返回的结果会总动转换为JSON格式
@RequestMapping("/favorite")  //该控制器处理的请求路径都以 /favorite 开头
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    /**
     * 添加收藏
     *
     * @param shUserId 用户id
     * @param favorite 收藏信息 从请求体中获取
     * @return 添加结果 使用R对象封装
     */
    @PostMapping("/add")
    public R addFavorite(
            //从cookie中获取名为ShUserId的参数
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            //从请求体中获取Favorite对象
            @RequestBody Favorite favorite
    ) {
        //将用户id设置到Favorite对象中
        favorite.setUserId(Long.valueOf(shUserId));
        //设置收藏的创建时间为当前时间
        favorite.setCreateTime(new Date());
        //调用favoriteService的addFavorite方法添加收藏
        if (favoriteService.addFavorite(favorite)) {
            //添加成功，就返回采成功信息并携带收藏的id返回
            return R.success(favorite.getId());
        }
        return R.fail(ErrorMsg.FAVORITE_EXIT);
    }

    /**
     * 删除收藏
     *
     * @param id 收藏id，从请求参数中获取
     * @return 结果 使用R对象封装
     */
    @GetMapping("/delete")
    public R deleteFavorite(
            //从cookie中获取名为ShUserId的参数
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            //从请求参数中获取收藏的id
            @RequestParam Long id
    ) {
        //调用favoriteService的deleteFavorite方法删除收藏
        if (favoriteService.deleteFavorite(id)) {
            //删除成功，返回成功结果
            return R.success();
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 检查收藏状态  检查用户是否收藏了某商品
     *
     * @param shUserId 用户id 从cookie中获取
     * @param idleId   收藏id 从请求参数中获取
     * @return 结果 使用R对象封装
     */
    @GetMapping("/check")
    public R checkFavorite(
            //从cookie中获取名为ShUserId的参数
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            //从请求参数中获取收藏的id
            @RequestParam Long idleId
    ) {
        //调用favoriteService的isFavorite方法检查收藏状态
        //返回成功结果，并携带检查结果
        return R.success(favoriteService.isFavorite(Long.valueOf(shUserId), idleId));
    }

    /**
     * 我的收藏
     *
     * @param shUserId 用户d 从cookie中获取
     * @return 收藏列表 使用R对象封装
     */
    @GetMapping("/my")
    public R getMyFavorite(
            //从cookie中获取名为ShUserId的参数
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId
    ) {
        //调用favoriteService的getAllFavorite方法获取用户的所有收藏
        //返回成功结果，并携带收藏列表
        return R.success(favoriteService.getAllFavorite(Long.valueOf(shUserId)));
    }
}

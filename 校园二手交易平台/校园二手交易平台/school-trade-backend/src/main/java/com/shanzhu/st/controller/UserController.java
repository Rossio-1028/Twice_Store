package com.shanzhu.st.controller;

import com.shanzhu.st.entity.User;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.UserService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 用户相关 控制层
 *主要处理与用户相关的HTTP请求：
 *用户登录注册、退出登录、获取用户信息、修改用户信息和密码等
 */
@CrossOrigin   //允许跨域请求
@RestController  //RESTful风格服务器
@RequestMapping("user")  //这个控制器下的所有请求的基础路径为 /user
public class UserController {

    //通过依赖注入，引入UserService
    @Resource
    private UserService userService;

    // R  --一个自定义的通用响应类，用于封装接口返回的数据和状态信息

    /**
     * 注册账号
     *
     * user 用户信息
     * @return 结果
     */
    @PostMapping("sign-in")
    public R signIn(@RequestBody User user) {
        //设置用户的注册时间为当前时间
        user.setSignInTime(new Timestamp(System.currentTimeMillis()));

        //如果用户没有设置头像，则使用默认头像
        if (user.getAvatar() == null || "".equals(user.getAvatar())) {
            user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        }

        //调用UserService的userSignIn方法进行注册操作
        if (userService.userSignIn(user)) {
            //成功，则返回成功响应并携带用户信息
            return R.success(user);
        }
        //注册失败，返回失败响应并携带错误信息
        return R.fail(ErrorMsg.REGISTER_ERROR);
    }

    /**
     * 登录
     *
     * accountNumber 账号
     * userPassword  密码
     * @return 登录结果
     */
    @RequestMapping("login")
    public R login(
            //账号密码不能为空、为null
            @RequestParam("accountNumber") @NotEmpty @NotNull String accountNumber,
            @RequestParam("userPassword") @NotEmpty @NotNull String userPassword,
            HttpServletResponse response
    ) {
        //调用UserService的userLogin方法进行登录验证
        User user = userService.userLogin(accountNumber, userPassword);
        //如果用户不存在，返回登录失败响应并携带错误信息
        if (null == user) {
            return R.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }
        //如果用户被封禁，返回登录失败响应并携带错误信息
        if (user.getUserStatus() != null && user.getUserStatus().equals((byte) 1)) {
            return R.fail(ErrorMsg.ACCOUNT_Ban);
        }
        //用户会话管理
        //创建一个名为shUserId的cookie,值为用户的ID
        Cookie cookie = new Cookie("shUserId", String.valueOf(user.getId()));
        //设置cookie的路径为根路径
        cookie.setPath("/");
        //设置cookie可以被JavaScript访问
        cookie.setHttpOnly(false);
        //将cookie添加到响应中
        response.addCookie(cookie);
        //登录成功，返回成功响应并携带用户信息
        return R.success(user);
    }

    /**
     * 退出登录
     *
     *  shUserId 用户id
     * @return 结果
     */
    @RequestMapping("logout")
    public R logout(
            //从cookie中获取用户ID，不能为空、为null
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId, HttpServletResponse response
    ) {
        Cookie cookie = new Cookie("shUserId", shUserId);
        //设置cookie的有效期限为0，即删除该cookie
        cookie.setMaxAge(0);
        cookie.setPath("/");
        //增强安全性，防止跨站脚本攻击(XSS),设置cookie只能通过HTTP协议访问
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        //退出登录成功，返回成功响应
        return R.success();
    }

    /**
     * 获取用户信息
     *
     *  id 用户ID
     * @return 用户信息
     */
    @GetMapping("info")
    public R getOneUser(
            @CookieValue("shUserId") @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录")
            String id
    ) {
        //调用UserService的getUser方法来获取用户信息，并返回成功响应
        return R.success(userService.getUser(Long.valueOf(id)));
    }

    /**
     * 修改用户公开信息
     *
     *  id   用户id
     * user 用户信息
     * @return 修改结果
     */
    @PostMapping("/info")
    public R updateUserPublicInfo(@CookieValue("shUserId") @NotNull(message = "登录异常 请重新登录")
                                  @NotEmpty(message = "登录异常 请重新登录")
                                  String id, @RequestBody User user) {
        //从cookie中获取到的用户ID设置到用户对象中
        user.setId(Long.valueOf(id));
        //调用UserService的updateUserInfo方法进行信息修改操作
        if (userService.updateUserInfo(user)) {
            return R.success();
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }


    /**
     * 修改密码
     *
     * id          用户id
     * oldPassword 旧密码
     * newPassword 新密码
     * @return  结果
     */
    @GetMapping("/password")
    public R updateUserPassword(
            @CookieValue("shUserId") @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String id,
            @RequestParam("oldPassword") @NotEmpty @NotNull String oldPassword,
            @RequestParam("newPassword") @NotEmpty @NotNull String newPassword) {
        //调用UserService的updatePassword方法进行密码修改操作
        if (userService.updatePassword(newPassword, oldPassword, Long.valueOf(id))
        ) {
            return R.success();
        }
        return R.fail(ErrorMsg.PASSWORD_RESET_ERROR);
    }
}

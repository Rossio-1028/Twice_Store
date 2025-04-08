package com.shanzhu.st.controller;

import com.shanzhu.st.entity.Admin;
import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.entity.User;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.AdminService;
import com.shanzhu.st.service.IdleItemService;
import com.shanzhu.st.service.OrderService;
import com.shanzhu.st.service.UserService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 管理员相关 控制层
 *
 */
@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private IdleItemService idleItemService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    /**
     * 管理员登录
     *
     * @param accountNumber 账号
     * @param adminPassword 密码
     * @param session       session
     * @return 登录结果
     */
    @GetMapping("login")
    public R login(
            @RequestParam("accountNumber") @NotNull @NotEmpty String accountNumber,
            @RequestParam("adminPassword") @NotNull @NotEmpty String adminPassword,
            HttpSession session
    ) {
        Admin admin = adminService.login(accountNumber, adminPassword);
        if (null == admin) {
            return R.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }
        session.setAttribute("admin", admin);
        return R.success(admin);
    }

    /**
     * 管理员登出
     *
     * @param session session
     * @return 结果
     */
    @GetMapping("loginOut")
    public R loginOut(HttpSession session) {
        session.removeAttribute("admin");
        return R.success();
    }

    /**
     * 查询管理员列表
     *
     * @param session session
     * @param page    分页大小
     * @param nums    分页页数
     * @return 管理员列表
     */
    @GetMapping("list")
    public R getAdminList(
            HttpSession session,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nums", required = false) Integer nums
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p = 1;
        int n = 8;
        if (null != page) {
            p = page > 0 ? page : 1;
        }
        if (null != nums) {
            n = nums > 0 ? nums : 8;
        }
        return R.success(adminService.getAdminList(p, n));
    }

    /**
     * 添加管理员
     *
     * @param session session
     * @param admin   管理员信息
     * @return 结果
     */
    @PostMapping("add")
    public R addAdmin(
            HttpSession session,
            @RequestBody Admin admin
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        if (adminService.addAdmin(admin)) {
            return R.success();
        }
        return R.fail(ErrorMsg.PARAM_ERROR);
    }

    /**
     * 违规状态列表
     *
     * @param session session
     * @param status  状态
     * @param page    分页大小
     * @param nums    分页页数
     * @return 管理员列表
     */
    @GetMapping("idleList")
    public R idleList(
            HttpSession session,
            @RequestParam("status") @NotNull @NotEmpty Integer status,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nums", required = false) Integer nums
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p = 1;
        int n = 8;
        if (null != page) {
            p = page > 0 ? page : 1;
        }
        if (null != nums) {
            n = nums > 0 ? nums : 8;
        }
        return R.success(idleItemService.adminGetIdleList(status, p, n));
    }

    /**
     * 更新违规状态
     *
     * @param session session
     * @param id      用户id
     * @param status  状态
     * @return 结果
     */
    @GetMapping("updateIdleStatus")
    public R updateIdleStatus(
            HttpSession session,
            @RequestParam("id") @NotNull @NotEmpty Long id,
            @RequestParam("status") @NotNull @NotEmpty Integer status
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        IdleItem idleItem = new IdleItem();
        idleItem.setId(id);
        idleItem.setIdleStatus(status.byteValue());
        if (idleItemService.updateIdleItem(idleItem)) {
            return R.success();
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 查询订单列表
     *
     * @param session session
     * @param page    分页大小
     * @param nums    页数
     * @return 结果
     */
    @GetMapping("orderList")
    public R orderList(
            HttpSession session,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nums", required = false) Integer nums
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p = 1;
        int n = 8;
        if (null != page) {
            p = page > 0 ? page : 1;
        }
        if (null != nums) {
            n = nums > 0 ? nums : 8;
        }
        return R.success(orderService.getAllOrder(p, n));
    }

    /**
     * 删除订单
     *
     * @param session session
     * @param id      订单id
     * @return 结果
     */
    @GetMapping("deleteOrder")
    public R deleteOrder(
            HttpSession session,
            @RequestParam("id") @NotNull @NotEmpty Long id
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        if (orderService.deleteOrder(id)) {
            return R.success();
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 用户列表
     *
     * @param session session
     * @param page    分页大小
     * @param nums    页数
     * @param status  状态
     * @return 列表
     */
    @GetMapping("userList")
    public R userList(
            HttpSession session,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "nums", required = false) Integer nums,
            @RequestParam("status") @NotNull @NotEmpty Integer status
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p = 1;
        int n = 8;
        if (null != page) {
            p = page > 0 ? page : 1;
        }
        if (null != nums) {
            n = nums > 0 ? nums : 8;
        }
        return R.success(userService.getUserByStatus(status, p, n));
    }

    /**
     * 更新用户状态
     *
     * @param session session
     * @param id      id
     * @param status  状态
     * @return 结果
     */
    @GetMapping("updateUserStatus")
    public R updateUserStatus(
            HttpSession session,
            @RequestParam("id") @NotNull @NotEmpty Long id,
            @RequestParam("status") @NotNull @NotEmpty Integer status
    ) {
        if (session.getAttribute("admin") == null) {
            return R.fail(ErrorMsg.COOKIE_ERROR);
        }
        User user = new User();
        user.setId(id);
        user.setUserStatus(status.byteValue());
        if (userService.updateUserInfo(user)) {
            return R.success();
        }

        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

}

package com.shanzhu.st.controller;

import com.shanzhu.st.entity.Message;
import com.shanzhu.st.enums.ErrorMsg;
import com.shanzhu.st.service.MessageService;
import com.shanzhu.st.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 留言 控制层
 * 负责处理与留言相关的HTTP请求，通过调用MessageService中的方法来完成具体业务逻辑
 *
 */
@CrossOrigin //允许跨域请求
@RestController //标识为RESTful风格的控制器
@RequestMapping("/message")  //请求URL前缀为 /message
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 发送留言
     *
     * @param shUserId 用户id 从cookie中获取
     * @param message  留言信息 从请求体中获取
     * @return 发送结果 使用R封装返回信息
     */
    @PostMapping("/send")
    public R sendMessage(
            //从cookie中获取名为ShUserId的值
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId,
            //从请求体中获取Message对象
            @RequestBody Message message
    ) {
        //设置留言的用户id
        message.setUserId(Long.valueOf(shUserId));
        //设置留言的创建时间为当前时间
        message.setCreateTime(new Date());
        //调用messageService的addMessage方法添加留言
        if (messageService.addMessage(message)) {
            //添加成功，返回包含留言信息的成功响应
            return R.success(message);
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }

    /**
     * 查询留言信息
     *
     * @param id 留言id 从请求参数中获取
     * @return 留言信息 使用R封装返回信息
     */
    @GetMapping("/info")
    //调用messageService的getMessage方法获取留言信息并返回成功响应
    public R getMessage(@RequestParam Long id) {
        return R.success(messageService.getMessage(id));
    }

    /**
     * 获取某个用户收到的所有留言
     *
     * @param idleId 闲置商品id 从请求参数中获取
     * @return 用户留言 使用R类封装返回信息
     */
    @GetMapping("/idle")
    public R getAllIdleMessage(@RequestParam Long idleId) {
        return R.success(messageService.getAllIdleMessage(idleId));
    }

    /**
     * 获取我的留言
     *
     * @param shUserId 用户id，从cookie中获取
     * @return 留言 使用R类封装返回信息
     */
    @GetMapping("/my")
    public R getAllMyMessage(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @NotEmpty(message = "登录异常 请重新登录") String shUserId
    ) {
        return R.success(messageService.getAllMyMessage(Long.valueOf(shUserId)));
    }

    /**
     * 删除留言
     *
     * @param id 留言id 从请求参数中获取
     * @return 删除结果 使用R类封装返回信息
     */
    @GetMapping("/delete")
    public R deleteMessage(
            @CookieValue("shUserId")
            @NotNull(message = "登录异常 请重新登录")
            @RequestParam Long id
    ) {
        //调用messageService的deleteMessage方法删除留言
        if (messageService.deleteMessage(id)) {
            return R.success();
        }
        return R.fail(ErrorMsg.SYSTEM_ERROR);
    }
}

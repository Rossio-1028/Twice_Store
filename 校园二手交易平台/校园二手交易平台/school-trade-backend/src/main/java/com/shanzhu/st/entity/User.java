package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 实体对象
 *
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 账号（手机号）
     */
    private String accountNumber;

    /**
     * 登录密码
     */
    private String userPassword;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册时间
     */
    private Date signInTime;

    private Byte userStatus;

}
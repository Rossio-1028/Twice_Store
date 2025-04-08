package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理员 实体对象
 *
 * @author: ShanZhu
 * @date: 2024-01-05
 */
@Data
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 管理员账号
     */
    private String accountNumber;

    /**
     * 密码
     */
    private String adminPassword;

    /**
     * 管理员名字
     */
    private String adminName;

}
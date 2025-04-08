package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 留言 实体对象
 *
 * @author: ShanZhu
 * @date: 2024-01-05
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户主键id
     */
    private Long userId;

    private User fromU;

    /**
     * 闲置主键id
     */
    private Long idleId;

    private IdleItem idle;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言时间
     */
    private Date createTime;

    /**
     * 所回复的用户
     */
    private Long toUser;

    private User toU;

    /**
     * 所回复的留言
     */
    private Long toMessage;

    private Message toM;

}
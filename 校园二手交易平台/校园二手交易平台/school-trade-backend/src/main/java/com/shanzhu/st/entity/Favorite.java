package com.shanzhu.st.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏 实体对象
 *
 * @author: ShanZhu
 * @date: 2024-01-05
 */
@Data
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键id
     */
    private Long id;

    /**
     * 加入收藏的时间
     */
    private Date createTime;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 闲置物主键id
     */
    private Long idleId;

    private IdleItem idleItem;
}
package com.shanzhu.st.service;

import com.shanzhu.st.entity.Message;

import java.util.List;

/**
 * 留言 业务服务层接口
 * 方便后续的实现类进行具体的业务逻辑处理
 *
 */
public interface MessageService {

    /**
     * 发送留言
     *
     * @param message 留言内容
     * @return 结果
     */
    boolean addMessage(Message message);

    /**
     * 删除留言
     *
     * @param id 留言id
     * @return 结果
     */
    boolean deleteMessage(Long id);

    /**
     * 获取某个留言
     *
     * @param id 留言id
     * @return 结果
     */
    Message getMessage(Long id);

    /**
     * 获取某个用户收到的所有留言
     *
     * @param userId 用户id
     * @return 留言列表
     */
    List<Message> getAllMyMessage(Long userId);

    /**
     * 获取某个闲置的所有留言
     *
     * @param idleId 闲置id
     * @return 结果
     */
    List<Message> getAllIdleMessage(Long idleId);

}

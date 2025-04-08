package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.IdleItem;
import com.shanzhu.st.entity.Message;
import com.shanzhu.st.entity.User;
import com.shanzhu.st.mapper.IdleItemMapper;
import com.shanzhu.st.mapper.MessageMapper;
import com.shanzhu.st.mapper.UserMapper;
import com.shanzhu.st.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    //使用@Resource注解注入MessageMapper实例，用于操作Message表
    @Resource
    private MessageMapper messageMapper;

    //使用@Resource注解注入UserMapper实例，用于操作User表
    @Resource
    private UserMapper userMapper;

    //使用@Resource注解注入IdleItemMapper实例，用于操作IdleItem表
    @Resource
    private IdleItemMapper idleItemMapper;

    /*
     *添加消息的方法
     * message 要添加的消息对象
     * @return 如果消息插入成功返回true，否则返回false
     */
    public boolean addMessage(Message message) {
        //调用messageMapper的insert方法插入信息，并判断插入结果是否为1（表示插入成功）
        return messageMapper.insert(message) == 1;
    }

    /*
     *删除消息的方法
     * id 要删除的消息ID
     * @return 如果消息删除功返回true，否则返回false
     */
    public boolean deleteMessage(Long id) {
        //调用messageMapper的deleteByPrimaryKey方法根据消息ID删除消息，并判断
        return messageMapper.deleteByPrimaryKey(id) == 1;
    }

    /*
     *根据消息ID获取消息的方法
     * id 要获取的消息ID
     * @return 返回对应ID的消息对象，不存在就返回null
     */
    public Message getMessage(Long id) {
        //调用messageMapper的selectByPrimaryKey方法根据消息ID查询消息
        return messageMapper.selectByPrimaryKey(id);
    }

    /*
     *获取当前用户所有消息的方法
     * userid 当前用户ID
     * @return 返回当前用户的所有消息列表
     */
    public List<Message> getAllMyMessage(Long userId) {
        //调用messageMapper的getMyMessage方法根据用户ID查询改用的所有消息
        List<Message> list = messageMapper.getMyMessage(userId);
        if (list.size() > 0) {
            //用于存储消息中涉及的用户ID列表
            List<Long> idList = new ArrayList<>();
            //遍历消息列表，将每个消息中的用户ID添加到idList中
            for (Message i : list) {
                idList.add(i.getUserId());
            }
            //调用userMapper的findUserByList方法根据用户ID列表查询对应的用户信息
            List<User> userList = userMapper.findUserByList(idList);
            //用于存储用户ID到用户对象的映射
            Map<Long, User> map = new HashMap<>();
            //遍历用户列表，将用户ID和对应的用户对象存入到map中
            for (User user : userList) {
                map.put(user.getId(), user);
            }
            //遍历消息列表，为每个消息设置发送者用户信息
            for (Message i : list) {
                i.setFromU(map.get(i.getUserId()));
            }
            //用于存储消息中涉及的闲置商品ID列表
            List<Long> idleIdList = new ArrayList<>();
            //遍历消息列表，将每个消息中的闲置商品ID添加到idleIdList中
            for (Message i : list) {
                idleIdList.add(i.getIdleId());
            }
            //调用idleItemMapper的findIdleByList方法根据闲置商品ID列表查询对应的闲置商品信息
            List<IdleItem> idleList = idleItemMapper.findIdleByList(idleIdList);
            //用于存储闲置商品ID到闲置商品对象的映射
            Map<Long, IdleItem> idleMap = new HashMap<>();
            //遍历闲置物品列表，将闲置物品ID和对应的闲置商品对象存入idleMap中
            for (IdleItem idle : idleList) {
                idleMap.put(idle.getId(), idle);
            }
            //遍历消息列表，为每个消息设置关联的闲置商品信息
            for (Message i : list) {
                i.setIdle(idleMap.get(i.getIdleId()));
            }
        }
        return list;
    }

    /*
     *获取指定闲置商品的所有消息的方法
     * idleid 闲置商品ID
     * @return 返回指定闲置商品的所有消息列表
     */
    public List<Message> getAllIdleMessage(Long idleId) {
        //调用messageMapper的getIdleMessage方法根据闲置商品ID查询该闲置商品的所有消息
        List<Message> list = messageMapper.getIdleMessage(idleId);
        if (list.size() > 0) {
            //用于存储消息中涉及的用户ID列表
            List<Long> idList = new ArrayList<>();
            //遍历消息列表，将每个消息中的用户ID添加到idList中
            for (Message i : list) {
                idList.add(i.getUserId());
            }
            //调用userMapper的findUserByList方法根据用户ID列表查询对应的用户信息
            List<User> userList = userMapper.findUserByList(idList);
            //用于存储用户ID到用户对象的映射
            Map<Long, User> map = new HashMap<>();
            //遍历用户列表，将用ID和对应的用户对象存入map中
            for (User user : userList) {
                map.put(user.getId(), user);
            }
            //遍历消息列表为每个消息设置发送者用户信息
            for (Message i : list) {
                i.setFromU(map.get(i.getUserId()));
            }
            //用于存储消息ID到消息对象的映射
            Map<Long, Message> mesMap = new HashMap<>();
            //遍历消息列表，将消息ID和对应的消息对象存入mesMap中
            for (Message i : list) {
                mesMap.put(i.getId(), i);
            }
            //遍历消息列表，为每个消息设置回复的消息内容和回复的用户昵称
            for (Message i : list) {
                Message toM = new Message();
                User toU = new User();
                if (i.getToMessage() != null) {
                    //设置回复的消息内容
                    toM.setContent(mesMap.get(i.getToMessage()).getContent());
                    //设置回复的用户昵称
                    toU.setNickname(map.get(i.getToUser()).getNickname());
                }
                i.setToM(toM);
                i.setToU(toU);
            }
        }
        return list;
    }
}

package com.shanzhu.st.mapper;

import com.shanzhu.st.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 留言 数据持久层
 */
@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> getMyMessage(Long userId);

    List<Message> getIdleMessage(Long idleId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}
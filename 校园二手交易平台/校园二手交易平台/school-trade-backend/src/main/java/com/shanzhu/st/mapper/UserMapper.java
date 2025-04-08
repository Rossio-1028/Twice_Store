package com.shanzhu.st.mapper;

import com.shanzhu.st.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 数据持久层
 *定义了一个 MyBatis 的 Mapper 接口
 * 调用MyBatis 的映射文件 UserMapper.xml，UserMapper.xml与 User 实体类相关的数据库操作
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    Integer existsByAccountNumber(String accountNumber);
    int insert(User record);

    int insertSelective(User record);

    User userLogin(@Param("accountNumber") String accountNumber, @Param("userPassword") String userPassword);

    User selectByPrimaryKey(Long id);

    List<User> getUserList();

    List<User> findUserByList(List<Long> idList);

    List<User> getNormalUser(int begin, int nums);

    List<User> getBanUser(int begin, int nums);

    int countNormalUser();

    int countBanUser();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePassword(@Param("newPassword") String newPassword,
                       @Param("oldPassword") String oldPassword, @Param("id") Long id);
}
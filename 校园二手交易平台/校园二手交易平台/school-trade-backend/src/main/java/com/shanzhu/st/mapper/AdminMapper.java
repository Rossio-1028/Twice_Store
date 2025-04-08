package com.shanzhu.st.mapper;

import com.shanzhu.st.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员相关 数据持久层

 */
@Mapper
public interface AdminMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin login(@Param("accountNumber") String accountNumber, @Param("adminPassword") String adminPassword);

    List<Admin> getList(int begin, int nums);

    int getCount();
}
package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.User;
import com.shanzhu.st.mapper.UserMapper;
import com.shanzhu.st.service.UserService;
import com.shanzhu.st.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
* UserService的具体业务实现
*
*/

@Service
public class UserServiceImpl implements UserService {

    //依赖注入 引入UserMapper，实现具体的数据库操作
    @Resource
    private UserMapper userMapper;

    /*
     * 根据用户ID获取用户信息
     */
    public User getUser(Long id) {
        //调用UserMapper的selectByPrimaryKey方法，通过用户ID来查询用户信息
        return userMapper.selectByPrimaryKey(id);
    }

    /*
     * 用户登录验证
     */
    public User userLogin(String accountNumber, String userPassword) {
        //调用UserMapper的userLogin方法，根据账号和密码查询用户信息
        return userMapper.userLogin(accountNumber, userPassword);
    }

    /*
     * 用户注册
     */
    public boolean userSignIn(User user) {
        //检测账号是否已存在
        Integer result=userMapper.existsByAccountNumber(user.getAccountNumber());
        boolean accountExists=result!=null&&result>0;
        if(accountExists){
            return false; //账号已存在
        }

        //调用UserMapper的insert方法，将用户信息插入数据库
        //如果插入成功，insert方法返回1，以此来判断是否注册成功
        return userMapper.insert(user) == 1;
    }

    /*
     * 更新用户信息
     */
    public boolean updateUserInfo(User user) {
        //调用UserMapper的updateByPrimaryKeySelective方法，根据用户ID选择性地更新用户信息
        //如果更新成功，方法返回1，以此来判断是否更新信息成功
        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    /*
     * 更新用户密码
     */
    public boolean updatePassword(String newPassword, String oldPassword, Long id) {
        //调用UserMapper的updatePassword方法
        return userMapper.updatePassword(newPassword, oldPassword, id) == 1;
    }

    /*
     *根据用户状态分页获取用户列表
     */
    public PageVo<User> getUserByStatus(int status, int page, int nums) {
        List<User> list;
        int count = 0;
        if (status == 0) {
            //状态=0，查询正常用户的总记录数
            count = userMapper.countNormalUser();
            list = userMapper.getNormalUser((page - 1) * nums, nums);
        } else {
            //状态≠0 查询封禁用户的总记录数
            count = userMapper.countBanUser();
            list = userMapper.getBanUser((page - 1) * nums, nums);
        }
        //创建一个pageVO对象，将用户列表和总记录数封装并返回
        return new PageVo<>(list, count);
    }

}

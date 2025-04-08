package com.shanzhu.st.service;

import com.shanzhu.st.entity.Address;

import java.util.List;

/**
 * 地址相关 服务层接口
 *定义了一系列处理地址信息的方法，包括获取用户的所有地址、获取单个地址、新增地址、修改地址、删除地址
 *
 */
public interface AddressService {

    /**
     * 获取一个用户的所有地址信息
     *
     * @param userId 用户id 用于指定要获取地址信息的用户
     * @return 地址列表 包含该用户的所有地址信息，如果没有地址就返回空列表
     */
    List<Address> getAddressByUser(Long userId);

    /**
     * 获取单个地址的信息
     *
     * @param id     地址id 用于指定要获取的具体地址
     * @param userId 用户id 用于验证该地址是否属于指定用户
     * @return 地址信息 如果找到对应的地址就返回该地址对象，否则返回null
     */
    Address getAddressById(Long id, Long userId);

    /**
     * 新增地址信息
     *
     * @param address 地址信息 包括要新增的地址的详细信息
     * @return 结果 新增成功返回true，失败返回false
     */
    boolean addAddress(Address address);

    /**
     * 修改地址信息
     *
     * @param address 地址信息，包括要修改的地址的详细信息，地址的id用于确定要修改的地址
     * @return 结果 修改成功返回true，失败返回false
     */
    boolean updateAddress(Address address);

    /**
     * 删除地址信息
     *
     * @param address 地址信息，包含要删除的地址的详细信息，地址的id用于确定要删除的地址
     * @return 结果 删除成功返回true，失败返回false
     */
    boolean deleteAddress(Address address);
}

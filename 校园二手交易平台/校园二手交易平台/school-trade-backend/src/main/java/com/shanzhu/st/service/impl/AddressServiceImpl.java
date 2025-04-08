package com.shanzhu.st.service.impl;

import com.shanzhu.st.entity.Address;
import com.shanzhu.st.mapper.AddressMapper;
import com.shanzhu.st.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址相关 服务层实现类
 * 处理与地址信息相关的业务逻辑
 *
 */

@Service
public class AddressServiceImpl implements AddressService {

    //@Resource注解注入AddressMapper接口，用于与数据库进行交互
    @Resource
    private AddressMapper addressMapper;

    /*
     *根据用户ID获取该用户的所有地址信息
     * userId 用户ID
     * @return 该用户的地址列表
     */
    public List<Address> getAddressByUser(Long userId){
        //调用addressMapper的getAddressByUser方法，根据用户ID查询地址信息
        return addressMapper.getAddressByUser(userId);
    }

    /*
     * 根据用户ID和地址ID获取指定地址信息
     * id 地址ID
     * userId 用户ID
     * @return 如果地址属于该用户，则返回地址信息，否则返回null
     */
    public Address getAddressById(Long id, Long userId){
        //根据地址ID从数据库中查询地址信息
        Address address = addressMapper.selectByPrimaryKey(id);
        //检查查询到的地址是否属于指定用户
        if(userId.equals(address.getUserId())){
            return address;
        }
        return null;
    }

    /*
     * 添加新的地址信息
     * address 要添加的地址对象
     * @return 如果添加成功返回true，否则返回false
     */
    public boolean addAddress(Address address){
        //如果要添加的地址为默认地址
        if(address.getDefaultFlag()){
            //创建一个新的Address对象，将默认标志设置为false
            Address a=new Address();
            a.setDefaultFlag(false);
            a.setUserId(address.getUserId());
            //将一个用户的所有地址改为非默认地址
            addressMapper.updateByUserIdSelective(a);
        }else {
            //判断是否有默认地址，若无，则将当前地址设为默认地址
            List<Address> list= addressMapper.getDefaultAddress(address.getUserId());
            if(null==list||0==list.size()){
                address.setDefaultFlag(true);
            }
        }
        //调用addressMapper中的方法插入新的地址信息，并判断是否插入成功
        return addressMapper.insert(address)==1;
    }

    /*
     *更新地址信息
     * address 要更新的地址对象
     * @return 如果更行成功返回true ，否则返回false
     */
    public boolean updateAddress(Address address){
        //如果要更新的地址为默认地址
        if(address.getDefaultFlag()){
            //同新增地址时的逻辑，将该用户的所有地址设置为非默认地址
            Address a=new Address();
            a.setDefaultFlag(false);
            a.setUserId(address.getUserId());
            addressMapper.updateByUserIdSelective(a);
        }else{
            //若取消默认地址，则将第一个地址设置为默认地址
            List<Address> list= addressMapper.getAddressByUser(address.getUserId());
            for(Address a:list){
                //检查当前地址是否为默认地址且是要更新的地址
                if(a.getDefaultFlag()&& a.getId().equals(address.getId())){
                    //创建一个新的Address对象，将第一个地址设置为默认地址
                    Address a1=new Address();
                    a1.setId(list.get(0).getId());
                    a1.setDefaultFlag(true);
                    //调用addressMapper的updateByPrimaryKeySelective方法更新地址信息，并判断是否更新成功
                    return addressMapper.updateByPrimaryKeySelective(address)==1&&
                            addressMapper.updateByPrimaryKeySelective(a1)==1;
                }
            }
        }
        //调用addressMapper的updateByPrimaryKeySelective方法更新地址信息，并判断是否更新成功
        return addressMapper.updateByPrimaryKeySelective(address)==1;
    }

    /*
     *删除指定地址信息
     * address 要删除的地址对象
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean deleteAddress(Address address){
        //调用addressMapper的deleteByPrimaryKeyAndUser方法删除指定地址信息，并判断是否删除成功
        return addressMapper.deleteByPrimaryKeyAndUser(address.getId(), address.getUserId())==1;
    }
}

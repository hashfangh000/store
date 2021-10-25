package com.fh.store.service.impl;

import com.fh.store.entity.Address;
import com.fh.store.mapper.AddressMapper;
import com.fh.store.service.IAddressService;
import com.fh.store.service.IDistrictService;
import com.fh.store.service.ex.AddressCountLimitException;
import com.fh.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/** 新增收货地址的实现类*/
@Service
public class AddressServiceImpl implements IAddressService {
    //添加用户的收货地址的业务层依赖于DistrictService的业务层接口
    @Autowired
    private IDistrictService districtService;
    @Autowired
    private AddressMapper addressMapper;
    @Value("20")
    private int maxCount;
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //统计当前uid用户的收货地址的数量 ，超过maxCount则不能增加
        Integer count =addressMapper.countByUid(uid);
        if(count >= maxCount){
            throw new AddressCountLimitException("收货地址已达到上限(" + maxCount + ")!");
        }


        // 补全数据：省、市、区的名称
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        //添加地址
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;// 1表示默认， 0 表示不是默认
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if(rows != 1){
            throw new InsertException("插入用户的收获地址产生位置的异常！");
        }

    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {

    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {

    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        return null;
    }
}

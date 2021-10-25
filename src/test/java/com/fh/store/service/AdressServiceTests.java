package com.fh.store.service;

import com.fh.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("135486551321");
        address.setName("你好棒！");
        addressService.addNewAddress(1,"管理员",address);
    }

    @Test
    public void getByUid(){
        System.out.println(addressService.getByUid(1));
    }
}

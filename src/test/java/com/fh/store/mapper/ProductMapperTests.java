package com.fh.store.mapper;

import com.fh.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductMapperTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void findHotList(){
        List<Product> hotList = productMapper.findHotList();
        System.out.println(hotList);
    }

    @Test
    public void findById(){
        Product product = productMapper.findById(10000025);
        System.out.println(product);
    }

    @Test
    public void findNewList(){
        List<Product> hotList = productMapper.findNewList();
        System.out.println(hotList);
    }
}

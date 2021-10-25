package com.fh.store.service;


import com.fh.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;



@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @Autowired
    private IProductService productService;

    @Test
    public void findHotList(){
        List<Product> hotList = productService.findHotList();
        System.out.println("count=" + hotList.size());
        for (Product product : hotList) {
            System.out.println(product);
        }
    }

    @Test
    public void findById(){
        try {
            Product product = productService.findById(10000025);
            System.out.println(product);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findNewList(){
        List<Product> newList = productService.findHotList();
        System.out.println("count=" + newList.size());
        for (Product product : newList) {
            System.out.println(product);
        }
    }
}

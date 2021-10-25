package com.fh.store.mapper;

import com.fh.store.entity.Cart;
import com.fh.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//@SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith :表示启动这个单元测试类(单元测试类是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setCid(1);
        cart.setUid(1);
        cart.setPid(1);
        cart.setNum(10);
        Integer rows = cartMapper.insert(cart);
        System.out.println(rows);
    }

    @Test
    public void findByUidAndPid(){
        Integer uid = 1;
        Integer pid = 1;
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        System.out.println(result);
    }

    @Test
    public void updateNumById(){
        Integer result = cartMapper.updateNumByCid(1, 4, "张三", new Date());
        System.out.println(result);
    }

    @Test
    public void findVOByUid(){
        List<CartVO> result = cartMapper.findVOByUid(2);
        for (CartVO cartVO : result) {
            System.out.println(cartVO);
        }
    }

    @Test
    public void findByCid(){
        Integer cid = 4;
        System.out.println(cartMapper.findByCid(cid));
    }
}

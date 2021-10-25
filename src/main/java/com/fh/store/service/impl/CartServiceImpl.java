package com.fh.store.service.impl;

import com.fh.store.entity.Cart;
import com.fh.store.entity.Product;
import com.fh.store.mapper.CartMapper;
import com.fh.store.mapper.ProductMapper;
import com.fh.store.service.ICartService;
import com.fh.store.service.IProductService;
import com.fh.store.service.ex.AccessDeniedException;
import com.fh.store.service.ex.CartNotFoundException;
import com.fh.store.service.ex.InsertException;
import com.fh.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    /** 购物车的业务层依赖于购物车的持久层和商品的持久层*/
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        //如果当前浏览的商品不在购物车，即cart==null 此商品没有被添加到购物车中
        Date date = new Date();
        if (result == null){
            //当前商品
            //cart数据不完整 补全
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //价格：来自于商品中的数据
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            //补全4个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            //执行数据插入操作
            Integer rows = cartMapper.insert(cart);
            if (rows != 1){
                throw  new InsertException("插入数据时产生未知的异常！");
            }
        }else { //当前浏览的商品再购物车，增加num 的值
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows != 1){
                throw  new InsertException("插入数据时产生未知的异常！");
            }
        }

    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
//        System.out.println(result);
        if (result == null){
            throw new CartNotFoundException("尝试访问的购物车不存在！");
        }

        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }


        Integer num = result.getNum() + 1;
        Date date = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, username, date);
        if(rows != 1){
            throw new InsertException("修改商品数量时存在错误，请稍后再试！");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        return null;
    }


}

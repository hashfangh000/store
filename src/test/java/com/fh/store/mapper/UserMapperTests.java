package com.fh.store.mapper;

import com.fh.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith :表示启动这个单元测试类(单元测试类是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {
    //idea有检测功能，接口是不能够直接创建Bean的(动态代理技术)
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法：就可以单独独立运行，不用启动整个项目，就可以做单元测试
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何类型，不能给参数
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(6,"321","管理员",new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(6));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(3);
        user.setPhone("");
        user.setEmail("");
        user.setGender(1);
        System.out.println(userMapper.updateInfoByUid(user));
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(9,"upload/avatar.png","管理员",new Date());
    }
}

package com.fh.store.service;

import com.fh.store.entity.User;
import com.fh.store.service.ex.*;
import com.fh.store.util.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Service;
import java.util.Date;

//@SpringBootTest：表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith :表示启动这个单元测试类(单元测试类是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {
    //idea有检测功能，接口是不能够直接创建Bean的(动态代理技术)
    @Autowired
    private IUserService userService;
    /**
     * 单元测试方法：就可以单独独立运行，不用启动整个项目，就可以做单元测试
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何类型，不能给参数
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("test02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException exception) {
            //获取类的对象 再获取类的名称
            System.out.println(exception.getClass());
            //获取异常的具体描述信息
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("test01","123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(7, "管理员", "123", "321");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(7));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("135872322");
        user.setEmail("asd@qq.com");
        user.setGender(0);
        userService.changeInfo(7,"管理员",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(9,"upload/test.png","管理员");
    }
}

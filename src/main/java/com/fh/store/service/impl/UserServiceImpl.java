package com.fh.store.service.impl;

import com.fh.store.entity.User;
import com.fh.store.mapper.UserMapper;
import com.fh.store.service.IUserService;
import com.fh.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.jws.soap.SOAPBinding;
import java.util.*;
/*用户模块业务层的实现类*/
@Service    //service注解 ： 将当前类的对象交给Spring来管理 自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        //通过user参数获取传递过来的username
        String username = user.getUsername();
        //调用findByUsername(username)判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        //判断结果集是否不为null 则抛出用户名被占用的异常
        if(result != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用！");
        }
        //密码加密处理的实现：md5 算法的形式：412dasx-21dasxzcedadxvds123c-vcxdqw
        //(串 + password + 串)    ------  md5算法进行加密，连续加载三次
        //(盐值 + password + 盐值) ------ 盐值就是一个随机的字符串。
        String oldPassword = user.getPassword();
        //获取盐值(随机生成一个盐值)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值的记录
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密处理,忽略原有密码的强度提升了数据的安全性
        String md5password = getMD5Password(oldPassword, salt);
        //将加密之后的密码重写补全设置到user对象中
        user.setPassword(md5password);
        //补全其他数据：is_delete设置为0
        user.setIsDelete(0);
        //不全数据：4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        //执行业务逻辑功能的实现(rows == 1)
        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if(result == null){
            throw new UserNotFoundException("用户数据不存在！");
        }
        //检测用户的密码是否匹配
        //1.先获取到数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        //2.和用户的传递过来的密码进行比较
        //2.1。先获取盐值：上一次注册时所自动生成的盐值
        String salt = result.getSalt();
        //2.2 将用户的密码按照相同的md5算法的规则进行加密 比较加密后的密码是否相同
        String newMd5Password = getMD5Password(password, salt);
        //3将密码进行比较
        if(!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段是否为1表示被标记删除
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在！");
        }
        //减少数据 提升性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }


    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("原密码错误！");
        }
        //将新密码设置到数据库中，将新的密码进行加密 再去更新
        String newMd5Password = getMD5Password(newPassword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password,username, new Date());
        if (rows != 1){
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在！");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    /**
     * User 对象中的数据phone/email/gender,手动再将uid/username封装在user对象中
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
       User result = userMapper.findByUid(uid);
       if(result == null || result.getIsDelete() == 1){
           throw new UserNotFoundException("用户数据不存在！");
       }
       user.setUid(uid);
       user.setModifiedUser(username);
       user.setModifiedTime(new Date());

       Integer rows = userMapper.updateInfoByUid(user);
       if(rows != 1){
           throw new UpdateException("更新数据是产生未知的异常");
       }
    }


    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 检查查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检查查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    /** 定义一个md5算法加密处理     */
    private String getMD5Password(String password, String salt){
        //md5 加密算法方法的调用(进行三次加密)
        for (int i = 0; i < 3;i++){
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }
}
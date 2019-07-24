package com.baizhi.service;


import com.baizhi.entity.Month;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/16 0016
 */
public interface UserService {
    Map<String,Object> queryAllByPage(Integer page, Integer rows);
    String addUser(User user);//注册
    void romoveUser(User user);
    String modifiyUser(User user);

    void modifiyProfile(User user);
    //登录
    Map<String,Object> login(User user);
    List<User> queryAllExportUser();

    Map<String, Object> regist(User user);
    Map<String,Object>  queryByMonth();

    User queryByPhone(String phone);

    Map<String,Object> goEasy();
}

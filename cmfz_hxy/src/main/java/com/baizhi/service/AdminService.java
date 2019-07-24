package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;


/**
 * @Author
 * @create 2019/7/11 0011
 */

public interface AdminService {
    //Admin queryByUsername(Admin admin);
    Map<String,Object> queryByUsername(Admin admin);

    void logout(HttpServletRequest request);
}

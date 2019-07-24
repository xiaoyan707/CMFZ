package com.baizhi.dao;

import com.baizhi.entity.Admin;


/**
 * @Author
 * @create 2019/7/11 0011
 */
public interface AdminDao {
    Admin selectByUsername(String username);
}

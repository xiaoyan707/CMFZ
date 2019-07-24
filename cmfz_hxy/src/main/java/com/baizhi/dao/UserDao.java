package com.baizhi.dao;

import com.baizhi.entity.Month;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/16 0016
 */
public interface UserDao extends BaseDao{
    void delete(User user);

    List<User> selectAllExportUser();

    User selectOne(String phone);

    List<Month> queryByMonth();

    List<User> queryBySex(String sex);
}

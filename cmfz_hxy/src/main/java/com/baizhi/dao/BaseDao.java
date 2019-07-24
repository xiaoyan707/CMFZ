package com.baizhi.dao;

import java.util.List;

public interface BaseDao<T> {
    List<T> selectAll(Integer begin, Integer rows);
    Integer selectRecords();//总记录数
    void insert(T t);
    void update(T t);
    void delete(String id);
    void updateImg(T t);
}

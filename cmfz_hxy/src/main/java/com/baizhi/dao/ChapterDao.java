package com.baizhi.dao;

import com.baizhi.entity.Chapter;

import java.util.List;

/**
 * @Author
 * @create 2019/7/15 0015
 */
public interface ChapterDao{
    public Integer selectNumChapter();
    public List<Chapter> selectAll(String albumId,Integer begin, Integer rows);
    Integer selectRecords();//总记录数

    void instert(Chapter chapter);

    void update(Chapter chapter);

    void delete(String s);

    void updateDownPath(Chapter chapter);
}

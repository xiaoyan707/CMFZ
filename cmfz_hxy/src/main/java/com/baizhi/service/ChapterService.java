package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

/**
 * @Author
 * @create 2019/7/15 0015
 */
public interface ChapterService {
    Map<String,Object> queryAll(String id,Integer begin, Integer rows);

    String addCarousel(Chapter carousel);

    String update(Chapter carousel);

    void delete(String[] id);

    void updateDownPath(Chapter chapter);
}

package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

/**
 * @Author
 * @create 2019/7/15 0015
 */

public interface AlbumService {
    Map<String,Object> queryAll(Integer page, Integer rows);
    String add(Album album);
    void delete(String[] id);
    String update(Album album);

    void updateCover(Album album);
}

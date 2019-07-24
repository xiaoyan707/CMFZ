package com.baizhi.service;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;

import java.util.Map;

/**
 * @Author
 * @create 2019/7/16 0016
 */
public interface GuruService {
    Map<String,Object> queryAll(Integer page, Integer rows);
    String addGuru(Guru guru);
    String modifiyGuru(Guru guru);

    void romoveGuru(String[] id);

    void modifiyProfile(Guru guru);

}

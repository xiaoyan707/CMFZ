package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

/**
 * @Author
 * @create 2019/7/16 0016
 */
public interface ArticleService {
    Map<String,Object> queryAll(Integer page, Integer rows,String id);
    String addArticle(Article article);
    void romoveArticle(String[] id);
    String modifiyArticle(Article article);

    Article queryOne(String id);
}

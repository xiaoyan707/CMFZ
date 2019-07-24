package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author
 * @create 2019/7/16 0016
 */
public interface ArticleDao extends BaseDao{
    List<Article> selectAll(@Param("begin") Integer begin,@Param("rows") Integer rows, @Param("id") String id);

    Article selectOne(String id);
    Integer selectRecords(Article article);
}

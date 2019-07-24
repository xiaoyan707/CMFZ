package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
   /* @Autowired
    GuruDao guruDao;*/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> queryAll(Integer page, Integer rows,String id) {
       // String status=guruDao.selectStatus(id);
        HashMap<String, Object> map = new HashMap<>();
        Article article=new Article();
        article.setGuruId(id);
        //查询总条数
        Integer records = articleDao.selectRecords(article);
        //一共有多少页
        Integer total=records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;

        List<Article> list = articleDao.selectAll(begin,rows,id);
        //当前页
        map.put("page",page);
        //总记录数
        map.put("records",records);
        //总页数
        map.put("total",total);
        //查询出的集合
        map.put("rows",list);

        return map;
    }

    @Override
    public String addArticle(Article article) {
        String s = UUID.randomUUID().toString();
        article.setId(s);
        articleDao.insert(article);
        return s;
    }

    @Override
    public void romoveArticle(String[] id) {
        for (String s : id) {
            articleDao.delete(s);
        }
    }

    @Override
    public String modifiyArticle(Article article) {
        articleDao.update(article);
        return article.getId();
    }

    @Override
    public Article queryOne(String id) {
        Article article=articleDao.selectOne(id);
        return article;
    }
}

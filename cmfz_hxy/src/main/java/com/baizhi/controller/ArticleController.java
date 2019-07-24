package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows,String idd){
        Map<String, Object> map = articleService.queryAll(page, rows,idd);
        return map;
    }
    @RequestMapping("edit")
    public String edit(String oper, Article article,String[] id,String idd){
        if("add".equals(oper)){
            article.setGuruId(idd);
            articleService.addArticle(article);
        }else if("edit".equals(oper)){
            System.out.println("123");
            article.setGuruId(idd);
            articleService.modifiyArticle(article);
        }else if ("del".equals(oper)){
            articleService.romoveArticle(id);
        }
        return null;
    }
    @RequestMapping("addArticle")
    public void addArticle(Article article){
        articleService.addArticle(article);
        System.out.println(article);
    }
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile file, HttpServletRequest request){
        String filename = file.getOriginalFilename();
        String realPath = request.getSession().getServletContext().getRealPath("/articleImg");
        File file1 = new File(realPath);
        if(!file1.exists()){
            file1.mkdir();
        }
        Map<String, Object> map = new HashMap<>();
        try {
            file.transferTo(new File(realPath,filename));
            //kindeditor上传图片有返回值error和url
            map.put("error",0);
            map.put("url","http://localhost:8081/cmfz/articleImg/"+filename);
            return map;
        } catch (IOException e) {
            map.put("error",1);
            map.put("url","http://localhost:8081/cmfz/articleImg/"+filename);
            e.printStackTrace();
            return map;
        }
    }
    @RequestMapping("showAll")
    public Map<String,Object> showAll(HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("articleImg");
        File file = new File(realPath);
        String[] list = file.list();
        HashMap<String, Object> map = new HashMap<>();
        map.put("current_url","http://localhost:8081/cmfz/articleImg/");
        map.put("total_count",list.length);
        List<Object> lists = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            Map<String , Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            File file1 = new File(realPath,s);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("is_photo",true);
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",substring);
            map1.put("filename",s);
            map1.put("datetime",new Date());
            lists.add(map1);
        }
        map.put("file_list",lists);
        return map;

    }
    @RequestMapping("queryOne")
    public Article queryOne(String id){
        System.out.println("111111");
        Article article=articleService.queryOne(id);
        return article;
    }

}

package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import com.baizhi.service.ArticleService;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@RestController
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    GuruService guruService;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = guruService.queryAll(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(String oper, Guru guru, String[] id){
        System.out.println("*************");
        if("add".equals(oper)){
            String s = guruService.addGuru(guru);
            System.out.println(guru);
            return s;
        }else if("edit".equals(oper)){
            String s = guruService.modifiyGuru(guru);
            return s;
        }else if("del".equals(oper)){
            guruService.romoveGuru(id);
        }
        return null;
    }
    @RequestMapping("upload")
    public void fileupload(HttpServletRequest request, MultipartFile profile, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String filename = profile.getOriginalFilename();
        if(filename!=null&&filename.length()!=0){
        //获取文件目录的真实位置
        String realPath = request.getSession().getServletContext().getRealPath("uploadProfile");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }
        profile.transferTo(new File(realPath,filename));
        Guru guru=new Guru();
        guru.setId(id);
        System.out.println(id);
        guru.setProfile(filename);
        guruService.modifiyProfile(guru);
        }

    }
    @RequestMapping("updateStatus")
    public void updateStatus(Guru guru){
        System.out.println("1111111111111111");
        if (guru.getStatus().equals("激活")){
            Guru guru2=new Guru();
            guru2.setId(guru.getId());
            guru2.setStatus("冻结");
            guruService.modifiyGuru(guru2);
        }else{
            Guru guru2=new Guru();
            guru.setId(guru.getId());
            guru.setStatus("激活");
        }



    }

}

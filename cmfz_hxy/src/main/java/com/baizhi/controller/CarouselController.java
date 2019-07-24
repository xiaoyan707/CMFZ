package com.baizhi.controller;

import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author
 * @create 2019/7/12 0012
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(Integer page,Integer rows){

        Map<String,Object> map =carouselService.queryAll(page,rows);

        return map;
    }
    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request,  MultipartFile imgPath, HttpServletResponse response,String id) throws IOException {
        //获取原始文件名
        String filename = imgPath.getOriginalFilename();
        if(filename!=null&&filename.length()!=0){
        //获取文件目录的真实位置
        String realPath = request.getSession().getServletContext().getRealPath("uploadImg");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }
        imgPath.transferTo(new File(realPath,filename));
        Carousel carousel=new Carousel();
        carousel.setImgPath(filename);

        carousel.setId(id);
        carouselService.updateImgPath(carousel);
        }
    }
    @RequestMapping("edit")
    public String edit(Carousel carousel,String oper,String[] id) throws IOException {
        if ("add".equals(oper)){
            String s=carouselService.addCarousel(carousel);
            return s;
        } else if ("edit".equals(oper)){
            //修改
            String s=carouselService.update(carousel);
            return s;
        }else if("del".equals(oper)){
            carouselService.delete(id);

        }
        return null;
    }
}

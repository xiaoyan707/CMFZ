package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @create 2019/7/15 0015
 */
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @RequestMapping("/queryAll")
    public Map<String,Object> queryAll(Integer page, Integer rows){
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }
    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request, MultipartFile cover, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String filename = cover.getOriginalFilename();
        if (filename!=null&&filename.length()!=0){
        //获取文件目录的真实位置
        String realPath = request.getSession().getServletContext().getRealPath("uploadCoverAlbum");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }
        cover.transferTo(new File(realPath,filename));
        Album album=new Album();
        album.setCover(filename);
        album.setId(id);
        albumService.updateCover(album);
        }

    }
    @RequestMapping("edit")
    public String edit(Album album,String oper,String[] id){
        if ("add".equals(oper)){
            String s = albumService.add(album);
            return s;
        } else if ("edit".equals(oper)){
            //修改
            String s = albumService.update(album);
            return s;
        }else if("del".equals(oper)){
            albumService.delete(id);
        }
        return null;
    }
}

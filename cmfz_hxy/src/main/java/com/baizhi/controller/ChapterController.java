package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/15 0015
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @RequestMapping("/queryAll")
    public Map<String,Object> queryAll(String idd,Integer page, Integer rows){
        Map<String, Object> map = chapterService.queryAll(idd,page, rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(Chapter carousel, String oper, String[] id,String idd) throws IOException {
        if ("add".equals(oper)){
            carousel.setAlbumId(idd);
            String s = chapterService.addCarousel(carousel);
            return s;
        } else if ("edit".equals(oper)){
            //修改
            String s=chapterService.update(carousel);
            return s;
        }else if("del".equals(oper)){
            chapterService.delete(id);
        }
        return null;
    }
    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request, MultipartFile downPath, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String filename = downPath.getOriginalFilename();
        if(filename!=null&&filename.length()!=0){
        //获取文件目录的真实位置
        String realPath = request.getSession().getServletContext().getRealPath("uploadDownPath");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }

        BigDecimal size = new BigDecimal(downPath.getSize());
        BigDecimal mod = new BigDecimal(1024);
        //除两个1024，保留两位小数，进行四舍五入
        size = size.divide(mod).divide(mod).setScale(2, BigDecimal.ROUND_HALF_UP);
        double c=size.doubleValue();

        downPath.transferTo(new File(realPath,filename));
        Chapter chapter=new Chapter();
        chapter.setSize(c);
        chapter.setId(id);
        chapter.setDownPath(filename);
        chapterService.updateDownPath(chapter);
        }
    }
    //文件下载
    // 文件下载
   @RequestMapping("filedownload")
   public void filedownload(String fname, HttpSession session, HttpServletResponse response) throws IOException {
        // 获取音频目录的真实位置
        String realPath = session.getServletContext().getRealPath("/uploadDownPath");
        // 获取源文件的字节数组
        byte[] bs = FileUtils.readFileToByteArray(new File(realPath + "/" + fname));

        // 设置响应头信息（以附件形式下载）
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fname,"utf-8"));

        // 获取输出流
        ServletOutputStream os = response.getOutputStream();
        // 使用输出流输出
        os.write(bs);
    }

}

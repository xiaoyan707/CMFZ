package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.annotation.UserAnnotation;
import com.baizhi.entity.Guru;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryAllByPage")
    public Map<String,Object> queryAllByPage(Integer page,Integer rows){
        Map<String, Object> map = userService.queryAllByPage(page,rows);
        return map;
    }
    @RequestMapping("edit")
    public String edit(String oper, User user,String[] id){
        if("add".equals(oper)){
            String s = userService.addUser(user);
            return s;
        }else if("edit".equals(oper)){
            userService.modifiyUser(user);
            return null;
        }else if("del".equals(oper)){

            userService.modifiyUser(user);
        }
        return null;
    }
    @RequestMapping("upload")
    public void fileupload(HttpServletRequest request, MultipartFile profile, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String filename = profile.getOriginalFilename();
        if(filename!=null&&filename.length()!=0){
            //获取文件目录的真实位置
            String realPath = request.getSession().getServletContext().getRealPath("UserProfile");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdir();
            }
            profile.transferTo(new File(realPath,filename));
           User user=new User();
           user.setProfile(filename);
           user.setId(id);
            userService.modifiyProfile(user);
        }

    }
    @RequestMapping("exportUser")
    public void exportUser(HttpServletResponse response) throws IOException {
        List<User> list = userService.queryAllExportUser();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户","用户表"),
                User.class, list);
        response.setHeader("content-disposition","attachment;filename=user.xls");
        response.setHeader("content-Type","application/vnd.ms-excel");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    @RequestMapping("importUser")
    public void importUser(MultipartFile file) throws IOException, ParseException {
        System.out.println(file);
        //创建一个workbook去接收
        Workbook workbook=new HSSFWorkbook(file.getInputStream());
        //获取第一个表
        Sheet sheet = workbook.getSheetAt(0);
        //获取一共多少行
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i < lastRowNum-1; i++) {
             User user=new User();
            Row row = sheet.getRow(i+2);
           /* Cell cell = row.getCell(0);
            String id = cell.getStringCellValue();
            user.setId(id);*/

            Cell cell1 = row.getCell(1);
            user.setPhone(cell1.getStringCellValue());

            Cell cell2 = row.getCell(2);
            user.setPassword(cell2.getStringCellValue());
            Cell cell3 = row.getCell(3);
            user.setDharmaName(cell3.getStringCellValue());
            Cell cell4 = row.getCell(4);
            user.setProvince(cell4.getStringCellValue());
            Cell cell5 = row.getCell(5);
            user.setCity(cell5.getStringCellValue());
            Cell cell6 = row.getCell(6);
            user.setGender(cell6.getStringCellValue());
            Cell cell7 = row.getCell(7);
            user.setPersonalSign(cell7.getStringCellValue());
            Cell cell8 = row.getCell(9);
            user.setStatus(cell8.getStringCellValue());
            Cell cell9 = row.getCell(10);
            String value = cell9.getStringCellValue();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(value);
            user.setRegistTime(date);
            userService.addUser(user);
            workbook.close();
        }

    }
    @RequestMapping("login")
    public  Map<String,Object> login(User user,String code,HttpSession session,HttpServletRequest request){
        if(code!=null){
            String code1 =(String) request.getSession().getAttribute("code");
            //String code1 = (String) session.getAttribute("code");
            if(code.equals(code1)){
                User user1=userService.queryByPhone(user.getPhone());
                HashMap<String, Object> map = new HashMap<>();
                map.put("成功",user1);
                return map;
            }else{
                HashMap<String, Object> map = new HashMap<>();
                map.put("失败","验证码错误");
                return map;
            }
        }else{
            Map<String,Object> map = userService.login(user);
            return map;
        }
    }
    @RequestMapping("regist")
    public Map<String,Object> regist(User user){
        Map<String,Object> map=userService.regist(user);
        return map;
    }
    //统计每月人数
    @RequestMapping("queryByMonth")
    public  Map<String,Object> queryByMonth(){
        Map<String,Object> list = userService.queryByMonth();
        System.out.println(list);
        return list;
    }
    @RequestMapping("loginByCode")
    public  Map<String,Object> loginByCode(User user,HttpSession session){
       /* String s = UUID.randomUUID().toString();
        String substring = s.substring(s.length() - 4,s.length());
        System.out.println(substring);*/
        //String salt = MD5Utils.getSalt();
        String str = "123456789";
        StringBuffer substring = new StringBuffer();
        for(int i=0;i<6;i++){
            substring.append(str.charAt(new Random().nextInt(9)));
        }

        DefaultProfile profile=DefaultProfile.getProfile("cn-hangzhou", "LTAIlzYGSPoAnKiO", "fbiZsShEHXFxWc0I9PCELWRpoZRILH");
        IAcsClient client=new DefaultAcsClient(profile);
        String phone=user.getPhone();
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "魏may");
        request.putQueryParameter("TemplateCode", "SMS_171111511");
        request.putQueryParameter("TemplateParam", "{\"code\":"+substring.toString()+"}");
       session.setAttribute("code",substring.toString());
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",substring);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return map;
    }

}

package com.baizhi;

import com.baizhi.annotation.UserAnnotation;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/18 0018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPoi {
    @Autowired
    UserService userService ;
    @Test
    public void test1() throws IOException {
        Workbook workbook=new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户表");
        Row row = sheet.createRow(0);
        Class<User> userClass = User.class;
        Field[] declaredFields = userClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            UserAnnotation annotation = declaredField.getAnnotation(UserAnnotation.class);
            String name = annotation.name();
            Cell cell = row.createCell(i);
            cell.setCellValue(name);
        }

        File file=new File("D:/testPoi");
        if(!file.exists()){
            file.mkdir();
        }
        workbook.write(new FileOutputStream("D:/testPoi/第一个excel.xls"));
        workbook.close();
    }
    @Test
    public void test2(){
        Map<String, Object> map = userService.queryByMonth();
        System.out.println(map);
    }

}

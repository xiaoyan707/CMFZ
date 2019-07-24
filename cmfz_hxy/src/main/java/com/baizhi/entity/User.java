package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baizhi.annotation.UserAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "密码")
    private String password;
    @ExcelIgnore()
    private String salt;
    @Excel(name = "上师")
    private String dharmaName;
    @Excel(name = "省")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "性别")
    private String gender;
    @Excel(name = "个性签名")
    private String personalSign;
    @Excel(name = "头像",type = 2 ,width = 40 , height = 20)
    private String profile;
    @Excel(name = "状态")
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "注册时间",format = "yyyy-MM-dd")
    private Date registTime;

    @ExcelIgnore()
    private Integer count;
}

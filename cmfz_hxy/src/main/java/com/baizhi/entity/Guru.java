package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guru implements Serializable {
    private String id;
    private String name;
    private String profile;//头像
    private String status;
    private String sex;
}

package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author
 * @create 2019/7/19 0019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Month implements Serializable {
    private Integer id;
    private Integer count;
    private String month;
}

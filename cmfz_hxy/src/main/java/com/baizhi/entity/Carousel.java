package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Author
 * @create 2019/7/12 0012
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
public class Carousel implements Serializable {
    private String id;
    private String title;
    private String imgPath;
    private String status;
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private String createTime;
}

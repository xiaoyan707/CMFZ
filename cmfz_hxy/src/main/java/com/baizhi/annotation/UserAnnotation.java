package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author
 * @create 2019/7/18 0018
 *
 * 自定义注解
 */
@Retention(RetentionPolicy.RUNTIME)//指定自定义注解在什么时候使用，在运行时使用
@Target(ElementType.FIELD)//声明注解在什么地方使用
public @interface UserAnnotation {
    public String name();
}

package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/11 0011
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("login")
    public Map<String,Object> queryByUsername(Admin admin){
        System.out.println("1111111111111111111111");
        Map<String, Object> map = adminService.queryByUsername(admin);
        System.out.println(map);
        return map;

    }
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        adminService.logout(request);
        return "redirect:/jsp/login.jsp";
    }
}

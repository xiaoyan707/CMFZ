package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/11 0011
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public Map<String,Object> queryByUsername(Admin admin) {
        HashMap<String, Object> map = new HashMap<>();
        Admin admin1 = adminDao.selectByUsername(admin.getUsername());
        if(admin1==null){
            map.put("code",300);
            map.put("message","用户名不存在");
        }else if(admin1.getPassword().equals(admin.getPassword())){
            map.put("code",200);
            map.put("message","登陆成功");
        }else{
            map.put("code",400);
            map.put("message","密码错误");
        }
        return map;
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}

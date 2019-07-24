package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import com.baizhi.util.MD5Utils;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAllByPage(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<String,Object>();
        //查总记录
        Integer records = userDao.selectRecords();
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<User> list = userDao.selectAll(begin, rows);
        //当前页
        map.put("page",page);
        //总记录数
        map.put("records",records);
        //总页数
        map.put("total",total);
        //查询出的集合
        map.put("rows",list);

        return map;
    }

    @Override
    public String addUser(User user) {
        String salt = MD5Utils.getSalt();
        //String password = MD5Utils.getPassword(user.getPassword() + salt);
        user.setSalt(salt);
       // user.setPassword(password);
        String s = UUID.randomUUID().toString();
       // user.setStatus("激活");
        user.setId(s);
        userDao.insert(user);
        return s;
    }

    @Override
    public void romoveUser(User user) {
        userDao.delete(user);
    }

    @Override
    public String modifiyUser(User user) {
        /*String salt = user.getSalt();
        String password = MD5Utils.getPassword(user.getPassword() + salt);*/

        userDao.update(user);
        return user.getId();
    }

    @Override
    public void modifiyProfile(User user) {
        userDao.updateImg(user);
    }

    @Override
    public Map<String,Object> login(User user) {
        HashMap<String,Object> map = new HashMap<>();
        User user2=userDao.selectOne(user.getPhone());

        if(user2==null){
            map.put("error","-200");
            map.put("message","用户名不存在");
        }else{
            String password = MD5Utils.getPassword(user.getPassword() + user2.getSalt());
            if(!user2.getPassword().equals(password)){
                map.put("error","-300");
                map.put("message","密码错误");
            }else{
                map.put("200",user2);
            }
        }
        return map;
    }

    @Override
    public List<User> queryAllExportUser() {
        List<User> list=userDao.selectAllExportUser();
        return list;
    }
    @Override
    public Map<String, Object> regist(User user) {
        User user1 = userDao.selectOne(user.getPhone());
        HashMap<String, Object> map = new HashMap<>();
        if(user1!=null){
            map.put("error","400");
            map.put("message","手机号已存在");
        }else{
            String salt = MD5Utils.getSalt();
            String password = MD5Utils.getPassword(user.getPassword() + salt);
            user.setSalt(salt);
            user.setPassword(password);
            String s = UUID.randomUUID().toString();
            user.setStatus("激活");
            user.setId(s);
            map.put("成功",user);

            userDao.insert(user);

            HashMap<String, Object> map2 = new HashMap<>();
            List<Month> list= userDao.queryByMonth();//月
            ArrayList<String> list3 = new ArrayList<>();
            ArrayList<Integer> list4 = new ArrayList<>();
            for (Month month : list) {
                list3.add(month.getMonth());
                list4.add(month.getCount());
            }
            map2.put("month",list3);
            map2.put("month2",list4);

            //男性注册人数
            List<User> users=userDao.queryBySex("男");

            ArrayList<Object> arr = new ArrayList<>();
            for (User user2 : users) {
                HashMap<String, Object> map3 = new HashMap<>();
               map3.put("name",user2.getProvince());
               map3.put("value",user2.getCount());
               arr.add(map3);
            }
            map2.put("man",arr);

            List<User> women = userDao.queryBySex("女");
            ArrayList<Object> brr = new ArrayList<>();
            for (User user3 : women) {
                HashMap<String, Object> map4 = new HashMap<>();
                map4.put("name",user3.getProvince());
                map4.put("value",user3.getCount());
                brr.add(map4);
            }

            map2.put("women",brr);
            Gson gson=new Gson();
            String s1 = gson.toJson(map2);

            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-898a5edb18f24954977883e2093e5ddb");
            goEasy.publish("demo_channel", s1);
        }
        return map;
    }
    @Override
    public  Map<String,Object>  queryByMonth(){
        HashMap<String, Object> map = new HashMap<>();
        List<Month> list= userDao.queryByMonth();//月
        map.put("month",list);
        //男性注册人数
        List<User> users=userDao.queryBySex("男");
        map.put("man",users);
        //女性注册人数
        List<User> list2=userDao.queryBySex("女");
        map.put("women",list2);
        return map;
    }

    @Override
    public User queryByPhone(String phone) {
        User user2=userDao.selectOne(phone);
        return user2;
    }

    @Override
    public Map<String, Object> goEasy() {

        return null;
    }

}

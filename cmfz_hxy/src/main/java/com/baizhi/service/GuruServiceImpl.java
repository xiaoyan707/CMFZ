package com.baizhi.service;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author
 * @create 2019/7/16 0016
 */
@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer records = guruDao.selectRecords();
        Integer total=records%rows==0?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List list = guruDao.selectAll(begin, rows);
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
    public String addGuru(Guru guru) {
        String s = UUID.randomUUID().toString();
        guru.setId(s);
        guruDao.insert(guru);
        return s;
    }

    @Override
    public String modifiyGuru(Guru guru) {

        guruDao.update(guru);
        return guru.getId();
    }

    @Override
    public void romoveGuru(String[] id) {
        for (String s : id) {
            guruDao.delete(s);
        }
    }

    @Override
    public void modifiyProfile(Guru guru) {
        guruDao.updateImg(guru);
    }



}

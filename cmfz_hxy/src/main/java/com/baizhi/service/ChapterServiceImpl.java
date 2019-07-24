package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author
 * @create 2019/7/15 0015
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    @Override
    public Map<String, Object> queryAll(String id,Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        Integer records = chapterDao.selectRecords();
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;

        List<Chapter> list = chapterDao.selectAll(id,begin, rows);
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
    public String addCarousel(Chapter  chapter) {
        String s = UUID.randomUUID().toString();
        chapter.setId(s);
        chapterDao.instert(chapter);
        return s;
    }

    @Override
    public String update(Chapter chapter) {
        chapterDao.update(chapter);
        return chapter.getId();
    }

    @Override
    public void delete(String[] id) {
        for (String s : id) {
            chapterDao.delete(s);
        }
    }

    @Override
    public void updateDownPath(Chapter chapter) {
        chapterDao.updateDownPath(chapter);
    }
}

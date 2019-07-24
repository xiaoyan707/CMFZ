package com.baizhi.service;

import com.baizhi.dao.CarouselDao;
import com.baizhi.entity.Carousel;
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
 * @create 2019/7/12 0012
 */
@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    CarouselDao carouselDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String,Object> queryAll(Integer page, Integer rows) {
        Map<String,Object> map=new HashMap<String,Object>();
        //查总记录
        Integer records = carouselDao.selectRecords();
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<Carousel> list = carouselDao.selectAll(begin, rows);
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
    public String addCarousel(Carousel carousel) {
        String uuid = UUID.randomUUID().toString();
        carousel.setId(uuid);
        carouselDao.insert(carousel);
        return uuid;
    }

    @Override
    public String update(Carousel carousel) {

        carouselDao.update(carousel);
        return carousel.getId();
    }

    @Override
    public void delete(String[] id) {
        for (String s : id) {
            carouselDao.delete(s);
        }
    }

    @Override
    public void updateImgPath(Carousel carousel) {
        carouselDao.updateImgPath(carousel);
    }
}

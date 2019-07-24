package com.baizhi.service;
import com.baizhi.entity.Carousel;
import java.util.Map;

/**
 * @Author
 * @create 2019/7/12 0012
 */
public interface CarouselService {
    Map<String,Object> queryAll(Integer page, Integer rows);

    String  addCarousel(Carousel carousel);

    String update(Carousel carousel);

    void delete(String[] id);

    void updateImgPath(Carousel carousel);
}

package com.baizhi.dao;

import com.baizhi.entity.Carousel;

import java.util.List;

/**
 * @Author
 * @create 2019/7/12 0012
 */
public interface CarouselDao {
    void insert(Carousel carousel);

    List<Carousel> selectAll(Integer begin, Integer rows);

    void update(Carousel carousel);

    void delete(String id);

    void updateCarousel(String no, String id);

    String queryStatusById(String id);

    Integer selectRecords();

    void updateImgPath(Carousel carousel);
}

package cn.leyou.item.service;

import cn.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> queryCategoriesByPid(Long pid);

    List<Category> queryCategoryByBid(Long bid);

    List<String> queryCategoryNamesById(List<Long> ids);

    List<Category> queryAllByCid3(Long id);
}

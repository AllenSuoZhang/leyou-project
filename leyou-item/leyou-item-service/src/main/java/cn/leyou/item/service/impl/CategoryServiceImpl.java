package cn.leyou.item.service.impl;

import cn.leyou.item.mapper.CategoryMapper;
import cn.leyou.item.pojo.Category;
import cn.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父分类查询子分类
     * @author yhl
     * @param pid
     * @return
     */
    @Override
    public List<Category> queryCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }

    /**
     * 根据品牌id查询商品分类
     * @author yhl
     * @param bid
     * @return
     */
    @Override
    public List<Category> queryCategoryByBid(Long bid) {
        return this.categoryMapper.queryCategoryByBid(bid);
    }
}

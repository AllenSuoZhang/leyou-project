package cn.leyou.item.service.impl;

import cn.leyou.item.mapper.CategoryMapper;
import cn.leyou.item.pojo.Category;
import cn.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 查询分类名称
     * @param ids
     * @return
     */
    @Override
    public List<String> queryCategoryNamesById(List<Long> ids) {
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
//        List<String> names = new ArrayList<>();
//        for (Category category : categories) {
//            names.add(category.getName());
//        }
        List<String> categoryNames =
                categories.stream().map(category -> category.getName()).collect(Collectors.toList());
        return categoryNames;
    }

    /**
     * 根据3级分类id，查询1~3级的分类
     * @param id
     * @return
     */
    public List<Category> queryAllByCid3(Long id) {
        Category c3 = this.categoryMapper.selectByPrimaryKey(id);
        Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1,c2,c3);
    }
}

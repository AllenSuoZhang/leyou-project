package cn.leyou.item.service.impl;

import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.mapper.BrandMapper;
import cn.leyou.item.mapper.SpuMapper;
import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.Spu;
import cn.leyou.item.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuMapper spuMapper;


    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        //初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name", "%"+ key + "%").orEqualTo("letter",key);
        }

        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);

        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据品牌id获取此品牌实体数据
     * @param bid
     * @return
     */
    @Override
    public Brand queryBrandByBid(Long bid) {
        return this.brandMapper.selectByPrimaryKey(bid);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        //先新增brand
        this.brandMapper.insertSelective(brand);

        //再新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });

    }

    /**
     * 根据品牌id删除品牌
     * @param bid
     */
    @Transactional
    @Override
    public Boolean delBrandByBid(Long bid) {
        //先查询有没有此品牌的商品
        //初始化example对象
        //判断
        //有就返回false
        //无就直接删除
        //删除中间表

//        Example example = new Example(Spu.class);
//        Example.Criteria criteria = example.createCriteria();
//
//        criteria.andEqualTo("valid", 1).andEqualTo("brandId", bid);
//
//        List<Spu> spuList = this.spuMapper.selectByExample(example);

        Spu spu = new Spu();
        spu.setBrandId(bid);
        spu.setValid(true);

        List<Spu> spuList = this.spuMapper.select(spu);

        Map map = new HashMap();
        if (CollectionUtils.isEmpty(spuList)){
            this.brandMapper.deleteCategoryAndBrand(bid);
            int flag = this.brandMapper.deleteByPrimaryKey(bid);
            if (flag >= 1){
                return true;
            }
            return false;
        }
        return false;
    }
}

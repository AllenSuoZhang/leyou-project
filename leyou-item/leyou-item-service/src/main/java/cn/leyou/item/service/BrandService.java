package cn.leyou.item.service;

import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    Brand queryBrandByBid(Long bid);

    void saveBrand(Brand brand, List<Long> cids);

    Boolean delBrandByBid(Long bid);
}

package cn.leyou.goods.service;

import cn.leyou.goods.client.BrandClient;
import cn.leyou.goods.client.CategoryClient;
import cn.leyou.goods.client.GoodsClient;
import cn.leyou.goods.client.SpecificationClient;
import cn.leyou.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specificationClient;

    /**
     * 加载商品详情数据
     * @param spuId
     * @return
     */
    public Map<String, Object> loadData(Long spuId){
        Map<String, Object> map = new HashMap<>();

        //根据spuId查询spu对象
        Spu spu = this.goodsClient.querySpuById(spuId);

        //根据spuId查询spuDetail对象
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

        //根据多个分类id查询分类名称
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryCategoryNamesById(cids);
        List<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0;i < cids.size();i++){
            Map<String, Object> category = new HashMap<>();
            category.put("id", cids.get(i));
            category.put("name", names.get(i));
            categories.add(category);
        }

        //根据品牌id查询brand
        Brand brand = this.brandClient.queryBrandByBid(spu.getBrandId());

        //根据spuId查询skus
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spuId);

        //根据第三级分类id查询规格组
        List<SpecGroup> groups = this.specificationClient.querySpecsByCid(spu.getCid3());

        //根据分类id和search=false特殊的规格参数
        List<SpecParam> specParams = this.specificationClient.queryParams(null, spu.getCid3(), null, null);
        Map<Long, String> paramMap = new HashMap<>();
        specParams.forEach(specParam -> {
            paramMap.put(specParam.getId(), specParam.getName());
        });
        map.put("spu", spu);
        map.put("spuDetail", spuDetail);
        map.put("categories", categories);
        map.put("brand", brand);
        map.put("skus", skus);
        map.put("groups", groups);
        map.put("params", paramMap);

        return map;
    }
}

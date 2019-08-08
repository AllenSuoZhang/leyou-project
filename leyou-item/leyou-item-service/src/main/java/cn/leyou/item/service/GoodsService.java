package cn.leyou.item.service;

import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.bo.SpuBo;
import cn.leyou.item.pojo.Sku;
import cn.leyou.item.pojo.Spu;
import cn.leyou.item.pojo.SpuDetail;

import java.util.List;

public interface GoodsService {
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    void saveGoods(SpuBo spuBo);

    SpuDetail querySpuDetailBySpuId(Long spuId);

    List<Sku> querySkusBySpuId(Long spuId);

    void updateGoods(SpuBo spuBo);

    Spu querySpuById(Long id);
}

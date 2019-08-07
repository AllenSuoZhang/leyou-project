package cn.leyou.item.api;

import cn.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("brand")
public interface BrandApi {
    /**
     * 根据品牌id获取此品牌实体数据
     * @param bid
     * @return
     */
    @GetMapping("{bid}")
    public Brand queryBrandByBid(@PathVariable("bid") Long bid);
}
